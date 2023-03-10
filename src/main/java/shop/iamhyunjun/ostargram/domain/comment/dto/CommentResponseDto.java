package shop.iamhyunjun.ostargram.domain.comment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;

//    private String author;

    private Long createdBy;

    private String text;

    private LocalDateTime createdAt;

    private Integer totalLikes = 0;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
//        this.author = comment.getAuthor();
        this.createdBy = comment.getCreatedBy();
        this.text = comment.getText();
        this.createdAt = comment.getCreatedAt();
        this.totalLikes = comment.getTotalLikes();
    }
}
