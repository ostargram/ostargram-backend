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

import static shop.iamhyunjun.ostargram.exception.message.ExceptionMessageEnum.NOT_EXISTED_COMMENT;

@Service
@RequiredArgsConstructor
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentLikes clickLikesButton(Long commentId, User user) {
        Optional<CommentLikes> optionalCommentLikes = commentLikesRepository.findByCommentIdAndCreatedBy(commentId, user.getId());

        // 좋아요 한 적 없을 때
        if (optionalCommentLikes.isEmpty()) {
            Comment comment = commentRepository.findById(commentId).orElseThrow(
                    () -> new IllegalArgumentException(NOT_EXISTED_COMMENT.getMessage())
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

    public Boolean checkLikes(Long postId, User user) {
        Optional<CommentLikes> optionalCommentLikes = commentLikesRepository.findByCommentIdAndCreatedBy(postId, user.getId());

        if (optionalCommentLikes.isEmpty()) {
            commentRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException(NOT_EXISTED_COMMENT.getMessage()));
            return false;
        }

        return optionalCommentLikes.get().isLiked();
    }
}
