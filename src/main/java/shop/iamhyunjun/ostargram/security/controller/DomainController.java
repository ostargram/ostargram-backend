package shop.iamhyunjun.ostargram.security.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.user.dto.UserSignupRequestDto;
import shop.iamhyunjun.ostargram.domain.user.dto.UserSignupResponseDto;
import shop.iamhyunjun.ostargram.security.dto.IndexDto;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DomainController {

    @GetMapping("/")
    public ResponseEntity<IndexDto> index() {


        IndexDto indexDto = new IndexDto(200, "환영합니다 API 서버 입니다.");

        return new ResponseEntity<>(indexDto,
                HttpStatus.OK);
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<IndexDto> favicon() {


        IndexDto indexDto = new IndexDto(200, "환영합니다 API 서버 입니다. 파비콘은 건들지 마세요..");

        return new ResponseEntity<>(indexDto,
                HttpStatus.OK);
    }

}
