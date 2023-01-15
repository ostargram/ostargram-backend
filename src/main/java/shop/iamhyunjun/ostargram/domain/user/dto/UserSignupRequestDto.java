package shop.iamhyunjun.ostargram.domain.user.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class UserSignupRequestDto {

    @NotBlank
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9]).{4,10}$",
            message = "아이디 형식이 올바르지 않습니다")
    private String username;

    @NotBlank
    @Pattern(regexp = "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-])^.{8,15}$",
            message = "패스워드 형식이 올바르지 않습니다.")
    private String password;

    private String checkPassword;

    private String email;

    private String gender;
}