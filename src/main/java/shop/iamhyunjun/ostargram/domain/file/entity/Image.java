package shop.iamhyunjun.ostargram.domain.file.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;

import javax.persistence.*;

//@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    private MultipartFile multipartFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}