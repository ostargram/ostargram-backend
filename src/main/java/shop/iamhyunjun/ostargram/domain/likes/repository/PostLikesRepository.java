package shop.iamhyunjun.ostargram.domain.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.iamhyunjun.ostargram.domain.likes.entity.PostLikes;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    Optional<PostLikes> findByPostIdAndCreatedBy(Long postId, Long userId);
}
