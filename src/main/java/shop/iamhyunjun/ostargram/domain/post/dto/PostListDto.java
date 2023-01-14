package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;

import java.time.LocalDateTime;

@Data
@Slf4j
@NoArgsConstructor
public class PostListDto {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private Integer totalLikes = 0;

    public PostListDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.totalLikes = post.getTotalLikes();
    }
}
