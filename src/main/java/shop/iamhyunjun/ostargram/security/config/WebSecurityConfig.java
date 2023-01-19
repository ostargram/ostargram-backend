package shop.iamhyunjun.ostargram.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.iamhyunjun.ostargram.security.customfilter.CustomSecurityFilter;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsServiceImpl;
import shop.iamhyunjun.ostargram.security.dto.SecurityExceptionDto;
import shop.iamhyunjun.ostargram.security.exception.CustomAuthenticationEntryPoint;


import java.io.OutputStream;
import java.io.PrintWriter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final UserDetailsServiceImpl userDetailsService;


    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/users/**").permitAll()
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .antMatchers("/invalid").permitAll()
                .antMatchers("/expired").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .anyRequest().authenticated();


        // Custom 로그인 페이지 사용
        http.formLogin().loginPage("/users/login-page").permitAll();

        http.sessionManagement();
//                .invalidSessionUrl("/invalid")
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .expiredUrl("/expired")
//                .sessionRegistry(sessionRegistry());




        http.logout()
                .logoutUrl("/users/logout")
                .logoutSuccessHandler((request, response, authentication) -> {

                    try (OutputStream os = response.getOutputStream()) {

                        SecurityExceptionDto exceptionDto =
                                new SecurityExceptionDto(200, "로그아웃 성공!");

                        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                        response.setStatus(HttpStatus.OK.value());

                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.writeValue(os, exceptionDto);
                        os.flush();
                    }

                });



        // Custom Filter
        http.addFilterBefore(new CustomSecurityFilter(userDetailsService, passwordEncoder()), UsernamePasswordAuthenticationFilter.class);

        // cors 설정
        http.cors().configurationSource(corsConfigurationSource());

        // 403, 인가 실패 핸들러
//        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        /// 401 인증 실패 핸들러
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);


        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("authorization");
        configuration.addExposedHeader("*");

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;

    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}