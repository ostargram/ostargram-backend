package shop.iamhyunjun.ostargram.security.customfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
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
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        String username = null;
        String password = null;


        // 기존의 시큐리티 내부 로직은 인증 시 데이터를 폼(POST)데이터로 받을 때만 처리해 놓았음.
        // 그래서 기존에 JSON Data가 들어오면 아마 null 값이 담기는데
        // Object Mapper를 이용해서 JSON Type이 들어올 경우
        // JSON Data를 Dto에 역직렬화해 사용한다.

        // 컨텐츠 타입 가지고 온다.
        String contentType = request.getContentType();

        // request URI 가지고 온다.
        String requestUrl = request.getRequestURI();

        // 컨텐츠 타입이 null이 아니고 && 요청이 /users/login일 때만 진행
        if (contentType != null && "/users/login".equals(requestUrl)) {

            if (request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {

                ServletInputStream inputStream = request.getInputStream();
                String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

                // Json -> DTO 역직렬화
                LoginRequestDto jsonData = objectMapper.readValue(messageBody, LoginRequestDto.class);

                // json-request
                username = jsonData.getUsername();
                password = jsonData.getPassword();

            } else {

                // 만약 form-request 일 경우!
                username = request.getParameter("username");
                password = request.getParameter("password");
            }
        }

        log.info("request.getRequestURI() = " + request.getRequestURI());


        if (username != null && password != null && (request.getRequestURI().equals("/users/login") || request.getRequestURI().equals("/api/test-secured"))) {



                // 익셉션 포인트 1
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);


                // 익셉션 포인트 2
                // 비밀번호 확인
                if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                    request.setAttribute("exception", "비밀번호가 일치하지 않습니다.");
                    throw new IllegalAccessError("비밀번호가 일치하지 않습니다.");
                }

                // 인증 객체 생성 및 등록
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);



        }

        filterChain.doFilter(request, response);
    }
}