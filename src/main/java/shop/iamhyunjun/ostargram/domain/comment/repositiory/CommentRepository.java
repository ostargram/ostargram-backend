package shop.iamhyunjun.ostargram.domain.comment.repositiory;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    void deleteById(Long id);
}
