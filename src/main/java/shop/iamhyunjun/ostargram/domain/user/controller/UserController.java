package shop.iamhyunjun.ostargram.domain.user.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.user.dto.*;

import shop.iamhyunjun.ostargram.domain.user.service.UserService;
import shop.iamhyunjun.ostargram.security.message.ResponseMessage;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody @Validated UserSignupRequestDto userSignupRequestDto) throws Exception {


        UserSignupResponseDto userSignupResponseDto = userService.signup(userSignupRequestDto);

        return new ResponseEntity<>(userSignupResponseDto,
                HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {

        UserLoginResponseDto userLoginResponseDto =
                new UserLoginResponseDto(200, ResponseMessage.LOGIN_SUCCESS);


        return new ResponseEntity<>(userLoginResponseDto,
                HttpStatus.OK);
    }
}