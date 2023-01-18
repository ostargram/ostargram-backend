package shop.iamhyunjun.ostargram.domain.email.dto;

import lombok.Data;


@Data
public class EmailCheckSuccessResponseDto {
    private int status;
    private String message;


    public EmailCheckSuccessResponseDto(int status, String message) {
        this.status = status;
        this.message = message;

    }
}

