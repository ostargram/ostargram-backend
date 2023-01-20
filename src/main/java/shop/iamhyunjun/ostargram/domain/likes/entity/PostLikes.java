package shop.iamhyunjun.ostargram.domain.likes.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;
import shop.iamhyunjun.ostargram.security.entity.BaseEntity;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikes extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isLiked;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    public PostLikes(Post post) {
        this.isLiked = true;
        this.post = post;
        this.post.plusLike();
    }

    // 좋아요 취소 처리 로직
    public PostLikes cancelLikeButton() {
        this.isLiked = false;
        this.post.minusLike();
        return this;
    }

    // 좋아요 누름 처리 로직
    public PostLikes pressLikeButton() {
        this.isLiked = true;
        this.post.plusLike();
        return this;
    }
}
