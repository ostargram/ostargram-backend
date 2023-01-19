package shop.iamhyunjun.ostargram.security.customfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.iamhyunjun.ostargram.domain.user.controller.UserController;
import shop.iamhyunjun.ostargram.domain.user.dto.LoginRequestDto;
import shop.iamhyunjun.ostargram.domain.user.repository.UserRepository;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsServiceImpl;
import shop.iamhyunjun.ostargram.security.dto.SecurityExceptionDto;
import shop.iamhyunjun.ostargram.security.message.ResponseMessage;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ReadOnlyBufferException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String loginURL = "/users/login";
        String requestURI = request.getRequestURI();
        String contentType = request.getContentType();


        // 로드밸런서의 상태확인 때문에 / 접근은 로그 제외
        if(!"/".equals(requestURI)){
            log.info("requestURI = " + requestURI);
        }

        String username = null;
        String password = null;


        if (!"/".equals(requestURI)) {

            log.info("custom Filter JSESSION ID : " + request.getSession().getId());

        }


        // ID, PW 추출 로직
        // JSON Data로 Request가 왔을 때.
        // 역직렬화 과정 -> 기존 시큐리티는 JSON Data로 로그인 활용 불가능.
        if (contentType != null && loginURL.equals(requestURI)) {

            if (request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {

                ServletInputStream inputStream = request.getInputStream();
                String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

                // Json -> DTO 역직렬화
                LoginRequestDto jsonData = objectMapper.readValue(messageBody, LoginRequestDto.class);

                // json-request
                username = jsonData.getUsername();
                password = jsonData.getPassword();

            } else {

                // form-data(시큐리티 기본)
                username = request.getParameter("username");
                password = request.getParameter("password");
            }
        }

        // Swagger용 로그인, Swagger : contentType : null
        if (contentType == null) {
            username = request.getParameter("username");
            password = request.getParameter("password");
        }


        // 로그인 인증 로직
        if (username != null && password != null && (loginURL.equals(requestURI))) {
            try {

                // username(id)확인
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 비밀번호 확인
                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                    throw new IllegalArgumentException(ResponseMessage.LOGIN_FAIL_ID_OR_PASSWORD);
                }

                // 마지막으로 해당 이메일 인증을 완료 했는지 확인. -> 인증 안할 시 로그인 불가능.
                userDetailsService.userEmailCheck(username);

                // 인증 객체 생성 및 등록
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);

                log.info("Login ID : " + username);

            } catch (IllegalArgumentException e) {

                try (OutputStream os = response.getOutputStream()) {

                    SecurityExceptionDto exceptionDto =
                            new SecurityExceptionDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage());

                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());

                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(os, exceptionDto);
                    os.flush();
                }
            } catch (UsernameNotFoundException e) {
                try (OutputStream os = response.getOutputStream()) {

                    SecurityExceptionDto exceptionDto =
                            new SecurityExceptionDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage());

                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());


                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(os, exceptionDto);
                    os.flush();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}