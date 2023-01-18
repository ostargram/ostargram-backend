package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@NoArgsConstructor
public class PostDataDto {
    private Integer status;
    private String message;
    private Object Data;

    public PostDataDto(Integer status, String message, Object Data) {
        this.status = status;
        this.message = message;
        this.Data = Data;
    }
}