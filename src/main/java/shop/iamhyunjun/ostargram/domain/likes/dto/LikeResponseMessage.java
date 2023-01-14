package shop.iamhyunjun.ostargram.domain.likes.dto;

import lombok.Getter;

@Getter
public class LikeResponseMessage {
    private Integer status;
    private String message;

    public LikeResponseMessage(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
