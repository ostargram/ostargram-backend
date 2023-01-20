package shop.iamhyunjun.ostargram.domain.likes.dto;

import lombok.Getter;

/**
 * 좋아요 여부 응답
 */
@Getter
public class IsLikeResponse {
    private Integer status;
    private boolean isLiked;

    public IsLikeResponse(Integer status, boolean isLiked) {
        this.status = status;
        this.isLiked = isLiked;
    }

    public boolean getIsLiked() {
        return isLiked;
    }
}
