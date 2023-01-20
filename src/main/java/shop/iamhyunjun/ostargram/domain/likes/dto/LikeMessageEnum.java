package shop.iamhyunjun.ostargram.domain.likes.dto;

import lombok.Getter;

@Getter
public enum LikeMessageEnum {
    LIKE_MESSAGE("좋아요"),
    LIKE_CANCEL_MESSAGE("좋아요 취소");

    private String message;

    LikeMessageEnum(String message) {
        this.message = message;
    }
}
