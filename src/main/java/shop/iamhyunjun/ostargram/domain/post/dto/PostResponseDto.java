package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentResponseDto;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Slf4j
@NoArgsConstructor
public class PostResponseDto {

    private Long id;

    private String title;

    private String content;

    private String image;

    private LocalDateTime createdAt;

    private Integer totalLikes = 0;

    private List<CommentResponseDto> comments = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.createdAt = post.getCreatedAt();
        this.totalLikes = post.getTotalLikes();
        List<Comment> commentList = post.getCommentList();
        log.info("commentList = " + comments.size());
        for (Comment comment : commentList) {
            this.comments.add(new CommentResponseDto(comment));
        }
    }


}
