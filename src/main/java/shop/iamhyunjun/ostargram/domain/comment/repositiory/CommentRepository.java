package shop.iamhyunjun.ostargram.domain.comment.repositiory;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment save(Comment comment);

    void deleteById(Long id);
}
