package shop.iamhyunjun.ostargram.domain.likes.dto;

import lombok.Getter;

@Getter
public enum LikeMessage {
    LIKE_MESSAGE("좋아요"),
    LIKE_CANCEL_MESSAGE("좋아요 취소");

    private String message;

    LikeMessage(String message) {
        this.message = message;
    }
}
