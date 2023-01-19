package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostMessageDto {
    private Integer status;
    private String message;

    public PostMessageDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
