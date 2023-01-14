package shop.iamhyunjun.ostargram.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentResponseDto;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentSaveDto;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentUpdateDto;
import shop.iamhyunjun.ostargram.domain.comment.entity.Comment;
import shop.iamhyunjun.ostargram.domain.comment.repositiory.CommentRepository;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;
import shop.iamhyunjun.ostargram.domain.post.repositiory.PostRepository;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void commentSave(Long postId, CommentSaveDto commentSaveDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다")
        );
        commentRepository.save(new Comment(commentSaveDto, userDetails.getUser(), post));
    }

    public void updateComment(Long postId, CommentUpdateDto commentUpdateDto) {

    }

    public void delete(Long commentId) {

    }

    public List<CommentResponseDto> findComments(Long postId) {

        return null;
    }



}
