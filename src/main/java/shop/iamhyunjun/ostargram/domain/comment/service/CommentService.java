package shop.iamhyunjun.ostargram.domain.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentSaveDto;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentUpdateDto;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;
import shop.iamhyunjun.ostargram.domain.comment.repositiory.CommentRepository;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;
import shop.iamhyunjun.ostargram.domain.post.repositiory.PostRepository;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void commentSave(Long postId, CommentSaveDto commentSaveDto, UserDetailsImpl userDetails) throws IllegalAccessException {
        Post foundPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.")
        );
            commentRepository.save(new Comment(commentSaveDto, foundPost));
    }

    @Transactional
    public void updateComment(Long commentId, CommentUpdateDto commentUpdateDto, UserDetailsImpl userDetails) throws IllegalAccessException {
        log.info("commentId = " + commentId);
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다.")
        );
        log.info("foundComment = " + foundComment.getText());
        log.info("foundCommentUser = " + foundComment.getCreatedBy());
        log.info("user = " + userDetails.getUser().getId());

        if (userDetails.getUser().getId().equals(foundComment.getCreatedBy())) {
            foundComment.updateComment(commentUpdateDto);
        } else throw new IllegalAccessException("잘못된 접근입니다.");

    }

    //댓글 삭제
    @Transactional
    public void delete(Long commentId, UserDetailsImpl userDetails) throws IllegalAccessException {
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다.")
        );
        if (userDetails.getUser().getId().equals(foundComment.getCreatedBy())) {
            commentRepository.deleteById(commentId);
        } else throw new IllegalAccessException();
    }


}
