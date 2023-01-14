package shop.iamhyunjun.ostargram.domain.comment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentSaveDto;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentUpdateDto;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;
import shop.iamhyunjun.ostargram.domain.user.entity.User;
import shop.iamhyunjun.ostargram.security.entity.BaseEntity;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

//    @Column(nullable = false)
//    private String author;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private Integer totalLikes = 0;

    public Comment(CommentSaveDto commentSaveDto, User user, Post post) {

    }

    public void updateComment(CommentUpdateDto commentUpdateDto) {
//        this.author = commentUpdateDto.getAuthor();
        this.text = commentUpdateDto.getText();
    }

    public void plusLike() {
        this.totalLikes = totalLikes +1;
    }

    public void minusLike() {
        this.totalLikes = totalLikes - 1;
    }
}
