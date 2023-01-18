package shop.iamhyunjun.ostargram.domain.email.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.email.dto.EmailCheckSuccessResponseDto;
import shop.iamhyunjun.ostargram.domain.email.service.EmailService;
import shop.iamhyunjun.ostargram.domain.email.service.EmailServiceImpl;
import shop.iamhyunjun.ostargram.domain.user.dto.UserLoginResponseDto;
import shop.iamhyunjun.ostargram.security.message.ResponseMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/email/{emailCode}/{userId}")
    public ResponseEntity<EmailCheckSuccessResponseDto> emailConfirm(@PathVariable String emailCode, @PathVariable Long userId) throws Exception {

        emailService.emailLinkCheck(emailCode, userId);

        EmailCheckSuccessResponseDto emailCheckSuccessResponseDto =
                new EmailCheckSuccessResponseDto(200, ResponseMessage.EMAIL_SUCCESS_CHECK_LINK);

        return new ResponseEntity<>(emailCheckSuccessResponseDto,
                HttpStatus.OK);
    }
}
