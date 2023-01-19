package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
public class PostSaveDto {

    @NotBlank
    private String title;

    private String content;

    private MultipartFile image;

    public PostSaveDto(String title, String content, MultipartFile image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
