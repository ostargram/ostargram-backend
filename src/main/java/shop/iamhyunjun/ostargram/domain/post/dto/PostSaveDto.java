package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class PostSaveDto {

    @NotBlank
    private String title;

    private String content;

    private MultipartFile image;

}
