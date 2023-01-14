package shop.iamhyunjun.ostargram.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.user.dto.UserLoginResponseDto;
import shop.iamhyunjun.ostargram.domain.user.dto.UserSignupRequestDto;
import shop.iamhyunjun.ostargram.domain.user.dto.UserSignupResponseDto;

import shop.iamhyunjun.ostargram.domain.user.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody UserSignupRequestDto userSignupRequestDto) {


        UserSignupResponseDto userSignupResponseDto = userService.signup(userSignupRequestDto);

        return new ResponseEntity<>(userSignupResponseDto,
                HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@AuthenticationPrincipal UserDetails userDetails) {

        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(200, "로그인 성공!");


        return new ResponseEntity<>(userLoginResponseDto,
                HttpStatus.OK);
    }


}