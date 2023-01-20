package shop.iamhyunjun.ostargram.domain.user.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class LoginRequestDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}