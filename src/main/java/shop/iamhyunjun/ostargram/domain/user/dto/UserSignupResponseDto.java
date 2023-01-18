package shop.iamhyunjun.ostargram.domain.user.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Getter
@Builder
public class UserSignupResponseDto<T> {

    private int status;
    private String message;


    public UserSignupResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
