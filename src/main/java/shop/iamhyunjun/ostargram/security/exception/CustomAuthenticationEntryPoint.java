package shop.iamhyunjun.ostargram.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import shop.iamhyunjun.ostargram.security.dto.SecurityExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


// 인증 실패 익셉션 핸들러
@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {



    @Override   // 로그인제외 인증을 필요로 하는 API 요청시 인증을 하지 않았을 경우.
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {

        SecurityExceptionDto exceptionDto;

        if (!"/".equals(request.getRequestURI())) {
            log.warn("인증 실패" + request.getRequestURI());
        }

            exceptionDto = new SecurityExceptionDto(HttpStatus.UNAUTHORIZED.value(), "잘못된 요청입니다.");

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            try (OutputStream os = response.getOutputStream()) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writeValue(os, exceptionDto);
                os.flush();
            }


    }
}