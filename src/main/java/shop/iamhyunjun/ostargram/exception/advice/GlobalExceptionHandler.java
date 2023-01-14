package shop.iamhyunjun.ostargram.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.iamhyunjun.ostargram.exception.dto.ExceptionResponseMessage;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 올바르지 못한 요청 400
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseMessage> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        ExceptionResponseMessage message = new ExceptionResponseMessage(BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
    }

    // 권한 없음 예외 처리 403
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseMessage> illegalAccessExceptionExceptionHandler(IllegalAccessException exception) {
        ExceptionResponseMessage message = new ExceptionResponseMessage(FORBIDDEN.value(), exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
    }
}
