package shop.iamhyunjun.ostargram.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.servlet.HandlerExceptionResolver;
import shop.iamhyunjun.ostargram.security.dto.SecurityExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import shop.iamhyunjun.ostargram.security.dto.SecurityExceptionDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


// 인증 실패 익셉션 핸들러
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    private static final SecurityExceptionDto exceptionDto =
//            new SecurityExceptionDto(HttpStatus.UNAUTHORIZED.value(), "아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요."); //HttpStatus.UNAUTHORIZED.getReasonPhrase());



    @Override   // 로그인제외 인증을 필요로 하는 API 요청시 인증을 하지 않았을 경우.
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {

        SecurityExceptionDto exceptionDto;

        if (!"/".equals(request.getRequestURI())) {
            log.warn("인증 실패" + request.getRequestURI());
        }

//        String URI = request.getRequestURI();


//        if ("/users/login".equals(URI) || "/error".equals(URI)) {
//
//            exceptionDto = new SecurityExceptionDto(HttpStatus.UNAUTHORIZED.value(), "아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요.");
//
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//
//
//            try (OutputStream os = response.getOutputStream()) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                objectMapper.writeValue(os, exceptionDto);
//                os.flush();
//            }
//
//        } else {
            exceptionDto = new SecurityExceptionDto(HttpStatus.UNAUTHORIZED.value(), "잘못된 요청입니다.");

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            try (OutputStream os = response.getOutputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(os, exceptionDto);
                os.flush();
            }


//        }


    }
}