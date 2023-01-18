package shop.iamhyunjun.ostargram.domain.user.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@Slf4j
public class UserSignupRequestDto {

    @NotBlank
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9]).{4,10}$",
            message = "회원 가입 실패 / 아이디는 6~10자, 소문자, 숫자를 포함해야 합니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])^.{8,15}$",
            message = "회원 가입 실패 / 비밀번호는 8자 이상 소문자, 대문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank
    private String check_password;


    @NotBlank
    @Email(message = "이메일 형식이 틀립니다.")
    private String email;


    @NotBlank
    private String gender;


    @AssertTrue(message = "체크 비밀번호가 다릅니다.")
    public boolean isPasswordCheckValidation() {

        log.info("password Double Check");

        if (this.password.equals(this.check_password)) {
            return true;
        } else {
            throw new IllegalArgumentException("체크 비밀번호가 다릅니다");
        }
    }

    @AssertTrue
    public boolean isGenderCheck() {

        if ("male".equals(this.gender)) {
            return true;
        } else if ("female".equals(this.gender)) {
            return true;
        } else {
            throw new IllegalArgumentException("성별 선택이 잘못 되었습니다.");
        }

    }


}