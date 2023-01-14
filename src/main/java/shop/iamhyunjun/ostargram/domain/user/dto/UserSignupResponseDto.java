package shop.iamhyunjun.ostargram.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Getter
@Builder
public class UserSignupResponseDto<T> {

    private int statusCode;
    private String responseMessage;


    public UserSignupResponseDto(int statusCode, String responseMessage) {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
    }
}
