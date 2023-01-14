package shop.iamhyunjun.ostargram.domain.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentResponseDto;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Slf4j
@NoArgsConstructor
public class PostResponseDto {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private Integer totalLikes = 0;

    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.totalLikes = post.getTotalLikes();
        List<Comment> commentList1 = post.getCommentList();
        log.info("commentList = " + commentList.size());
        for (Comment comment : commentList1) {
            this.commentList.add(new CommentResponseDto(comment));
        }
    }


}
