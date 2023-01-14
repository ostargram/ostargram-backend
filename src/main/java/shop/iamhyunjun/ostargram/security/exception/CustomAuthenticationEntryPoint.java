package shop.iamhyunjun.ostargram.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
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
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final SecurityExceptionDto exceptionDto =
            new SecurityExceptionDto(HttpStatus.UNAUTHORIZED.value(), " 아이디 또는 비밀번호를 잘못 입력했습니다. 입력하신 내용을 다시 확인해주세요."); //HttpStatus.UNAUTHORIZED.getReasonPhrase());

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {


        System.out.println(authenticationException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        try (OutputStream os = response.getOutputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(os, exceptionDto);
            os.flush();
        }
    }
}