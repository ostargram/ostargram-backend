package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostSaveDto {

    @NotBlank
    private String author;

    @NotBlank
    private String title;

    private String content;

    private Long id;

}
