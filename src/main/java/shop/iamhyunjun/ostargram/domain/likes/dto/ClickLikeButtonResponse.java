package shop.iamhyunjun.ostargram.domain.likes.dto;

import lombok.Getter;

/**
 * 좋아요 버튼 이벤트 응답
 */
@Getter
public class ClickLikeButtonResponse {
    private Integer status;
    private String message;

    public ClickLikeButtonResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
