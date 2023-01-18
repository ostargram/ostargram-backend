package shop.iamhyunjun.ostargram.security.message;

import lombok.Getter;

import java.util.Arrays;


public class ResponseMessage {

    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL_ID_OR_PASSWORD = "로그인 실패 / ID 혹은 PW가 맞지 않습니다.";
    public static final String LOGIN_FAIL_CHECK_EMAIL = "로그인 실패 / 이메일 인증이 진행되지 않았습니다.";

    public static final String EMAIL_SUCCESS_CHECK_LINK = "인증 성공";
    public static final String EMAIL_FAIL_CHECK_LINK = "인증 실패 / 링크가 유효하지 않습니다.";
}