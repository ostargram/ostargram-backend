package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PostUpdateDto {

    @NotBlank
    private String title;

    private String content;
}
