package shop.iamhyunjun.ostargram.domain.user.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignupRequestDto {
    private String username;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}