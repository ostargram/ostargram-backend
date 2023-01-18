package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostMessageDto {
    private Integer status;
    private String message;

    public PostMessageDto(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
