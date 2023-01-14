package shop.iamhyunjun.ostargram.domain.post.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;
import shop.iamhyunjun.ostargram.domain.post.dto.PostSaveDto;
import shop.iamhyunjun.ostargram.domain.post.dto.PostUpdateDto;
import shop.iamhyunjun.ostargram.domain.user.entity.User;
import shop.iamhyunjun.ostargram.security.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

//    @Column(nullable = false)
//    private String author;

    @Column(nullable = false)
    private String title;

    private String content;

//    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    private Integer totalLikes = 0;

    public Post(PostSaveDto postSaveDto) {
        this.title = postSaveDto.getTitle();
        this.content = postSaveDto.getContent();
    }

    public void update(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
    }

    public void plusLike() {
        this.totalLikes = totalLikes +1;
    }

    public void minusLike() {
        this.totalLikes = totalLikes - 1;
    }

}
