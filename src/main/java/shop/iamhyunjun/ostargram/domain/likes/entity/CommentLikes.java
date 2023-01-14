package shop.iamhyunjun.ostargram.domain.likes.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;
import shop.iamhyunjun.ostargram.security.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikes extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isLiked;

    @ManyToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    public CommentLikes(Comment comment) {
        this.comment = comment;
        this.isLiked = true;
    }

    public CommentLikes cancelLikeButton() {
        this.isLiked = false;
        this.comment.minusLike();
        return this;
    }

    public CommentLikes pressLikeButton() {
        this.isLiked = true;
        this.comment.plusLike();
        return this;
    }
}
