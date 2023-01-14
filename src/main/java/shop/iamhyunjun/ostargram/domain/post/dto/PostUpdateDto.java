package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PostUpdateDto {

    @NotNull
    private String author;

    @NotBlank
    private String title;

    private String content;
}
