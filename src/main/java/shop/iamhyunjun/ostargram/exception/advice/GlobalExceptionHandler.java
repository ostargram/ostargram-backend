package shop.iamhyunjun.ostargram.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.iamhyunjun.ostargram.exception.dto.ExceptionResponseListMessage;
import shop.iamhyunjun.ostargram.exception.dto.ExceptionResponseMessage;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 올바르지 못한 요청 400
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseMessage> illegalArgumentExceptionHandle(IllegalArgumentException exception) {
        ExceptionResponseMessage message = new ExceptionResponseMessage(BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
    }

    // 권한 없음 예외 처리 403
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseMessage> illegalAccessExceptionHandle(IllegalAccessException exception) {
        ExceptionResponseMessage message = new ExceptionResponseMessage(FORBIDDEN.value(), exception.getMessage());
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
    }

    // 회원가입 - 유효성 검증 실패, 게시글 - 유효성 검증 실패
    @ExceptionHandler
    public ResponseEntity<ExceptionResponseListMessage> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        List<String> errors = bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ExceptionResponseListMessage message = new ExceptionResponseListMessage(BAD_REQUEST.value(), errors);
        return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
    }

}
