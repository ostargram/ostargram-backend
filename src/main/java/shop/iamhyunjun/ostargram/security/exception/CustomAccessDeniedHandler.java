//package shop.iamhyunjun.ostargram.security.exception;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import shop.iamhyunjun.ostargram.security.dto.SecurityExceptionDto;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.stereotype.Component;
//import shop.iamhyunjun.ostargram.security.dto.SecurityExceptionDto;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.OutputStream;
//
//
//// 인가 실패 익셉션 헨들러
//@Component
//public class CustomAccessDeniedHandler implements AccessDeniedHandler {
//    private static final SecurityExceptionDto exceptionDto =
//            new SecurityExceptionDto(HttpStatus.FORBIDDEN.value(), "인가되지 않은 사용자입니다.");
//
//    @Override
//    public void handle(HttpServletRequest request, HttpServletResponse response,
//                       AccessDeniedException accessDeniedException) throws IOException {
//
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//
//        try (OutputStream os = response.getOutputStream()) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.writeValue(os, exceptionDto);
//            os.flush();
//        }
//    }
//}