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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shop.iamhyunjun.ostargram.security.customfilter.CustomSecurityFilter;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsServiceImpl;
import shop.iamhyunjun.ostargram.security.dto.SecurityExceptionDto;
import shop.iamhyunjun.ostargram.security.exception.CustomAccessDeniedHandler;
import shop.iamhyunjun.ostargram.security.exception.CustomAuthenticationEntryPoint;

import java.io.PrintWriter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity// 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final UserDetailsServiceImpl userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf().disable();



        http.authorizeRequests().antMatchers("/users/**").permitAll()
                .anyRequest().authenticated();

        // Custom 로그인 페이지 사용
        http.formLogin().loginPage("/users/login-page").permitAll();

        // Custom Filter 등록하기
        http.addFilterBefore(new CustomSecurityFilter(userDetailsService, passwordEncoder()), UsernamePasswordAuthenticationFilter.class);


        /// 401 인증 실패
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);

        // 403, 인가 실패
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);


        return http.build();
    }

}