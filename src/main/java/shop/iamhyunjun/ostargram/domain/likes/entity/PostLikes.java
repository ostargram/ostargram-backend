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
    }

    public PostLikes cancelLikeButton() {
        this.isLiked = false;
        return this;
    }

    public PostLikes pressLikeButton() {
        this.isLiked = true;
        return this;
    }
}
