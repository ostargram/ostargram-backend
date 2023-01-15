package shop.iamhyunjun.ostargram.exception.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ExceptionResponseListMessage {
    private Integer status;
    private List<String> messages;

    public ExceptionResponseListMessage(Integer status, List<String> messages) {
        this.status = status;
        this.messages = messages;
    }
}
