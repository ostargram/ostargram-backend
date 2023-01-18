package shop.iamhyunjun.ostargram.exception.message;

import lombok.Getter;

@Getter
public enum ExceptionMessageEnum {

    NOT_EXISTED_POST("존재하지 않는 게시글입니다."),
    NOT_EXISTED_COMMENT("존재하지 않는 댓글입니다."),
    NOT_AUTHORITY_OF_UPDATE("수정은 본인만 가능합니다."),
    NOT_AUTHORITY_OF_DELETE("삭제는 본인만 가능합니다.");

    private String message;

    ExceptionMessageEnum(String message) {
        this.message = message;
    }
}
