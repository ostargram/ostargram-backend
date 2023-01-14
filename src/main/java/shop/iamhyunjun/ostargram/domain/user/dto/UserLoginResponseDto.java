package shop.iamhyunjun.ostargram.domain.user.dto;

import lombok.Data;

@Data
public class UserLoginResponseDto {

    private int status;
    private String message;


    public UserLoginResponseDto(int status, String message) {
        this.status = status;
        this.message = message;

    }
}