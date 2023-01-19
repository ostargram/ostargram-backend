package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PostUpdateDto {

    @NotBlank
    private String title;

    private String content;

//    private MultipartFile image;
}
