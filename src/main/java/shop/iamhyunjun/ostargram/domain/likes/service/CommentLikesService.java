package shop.iamhyunjun.ostargram.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;
import shop.iamhyunjun.ostargram.domain.comment.repositiory.CommentRepository;
import shop.iamhyunjun.ostargram.domain.likes.entity.CommentLikes;
import shop.iamhyunjun.ostargram.domain.likes.repository.CommentLikesRepository;
import shop.iamhyunjun.ostargram.domain.user.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentLikes execute(Long commentId, User user) {
        Optional<CommentLikes> optionalCommentLikes = commentLikesRepository.findByComment_IdAndCreatedBy(commentId, user.getId());

        // 좋아요 한 적 없을 때
        if (optionalCommentLikes.isEmpty()) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않은 게시글")
            );
            return commentLikesRepository.save(new CommentLikes(comment));
        }

        CommentLikes commentLikes = optionalCommentLikes.get();

        // 좋아요 버튼이 눌러져 있다면 -> 눌렀을 때 좋아요 취소
        if (commentLikes.isLiked()) {
            return commentLikes.cancelLikeButton();
        }
        // 좋아요 버튼이 눌러져 있지 않다면 -> 눌렀을 때 좋아요
        return commentLikes.pressLikeButton();
    }
}
