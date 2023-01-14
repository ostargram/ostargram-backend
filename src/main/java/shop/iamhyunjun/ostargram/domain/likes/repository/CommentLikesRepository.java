package shop.iamhyunjun.ostargram.domain.likes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import shop.iamhyunjun.ostargram.domain.likes.entity.CommentLikes;

import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    Optional<CommentLikes> findByComment_IdAndCreatedBy(Long commentId, Long userId);
}
