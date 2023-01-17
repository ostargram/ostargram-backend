package shop.iamhyunjun.ostargram.domain.likes.dto;

import lombok.Getter;

@Getter
public class LikeResponse {
    private Integer status;
    private boolean isLiked;

    public LikeResponse(Integer status, boolean isLiked) {
        this.status = status;
        this.isLiked = isLiked;
    }

    public boolean getIsLiked() {
        return isLiked;
    }
}
