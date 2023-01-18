package shop.iamhyunjun.ostargram.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.iamhyunjun.ostargram.domain.likes.entity.PostLikes;
import shop.iamhyunjun.ostargram.domain.likes.repository.PostLikesRepository;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;
import shop.iamhyunjun.ostargram.domain.post.repositiory.PostRepository;
import shop.iamhyunjun.ostargram.domain.user.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostLikes execute(Long postId, User user) {
        Optional<PostLikes> optionalPostLikes = postLikesRepository.findByPostIdAndCreatedBy(postId, user.getId());

        // 좋아요 한 적 없을 때
        if (optionalPostLikes.isEmpty()) {
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않은 게시글")
            );
            return postLikesRepository.save(new PostLikes(post));
        }

        PostLikes postLikes = optionalPostLikes.get();

        // 좋아요 버튼이 눌러져 있다면 -> 눌렀을 때 좋아요 취소
        if (postLikes.isLiked()) {
            return postLikes.cancelLikeButton();
        }
        // 좋아요 버튼이 눌러져 있지 않다면 -> 눌렀을 때 좋아요
        return postLikes.pressLikeButton();

    }

    public Boolean checkLikes(Long postId, User user) {
        Optional<PostLikes> optionalPostLikes = postLikesRepository.findByPostIdAndCreatedBy(postId, user.getId());

        if (optionalPostLikes.isEmpty()) {
            postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 게시글"));
            return false;
        }

        return optionalPostLikes.get().isLiked();
    }
}
