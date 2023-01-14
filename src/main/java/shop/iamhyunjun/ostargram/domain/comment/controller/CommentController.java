package shop.iamhyunjun.ostargram.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentSaveDto;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentUpdateDto;
import shop.iamhyunjun.ostargram.domain.comment.service.CommentService;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public String comment(@PathVariable Long postId, @RequestBody CommentSaveDto commentSaveDto,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        commentService.commentSave(postId,commentSaveDto,userDetails);
        return "commentSaved";
    }

    @PatchMapping("/comments/{commentId}")
    public String edit(@PathVariable Long commentId,
                       @Validated @RequestBody CommentUpdateDto commentUpdateDto,
                       @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        commentService.updateComment(commentId, commentUpdateDto, userDetails);
        return "commentEdited";
    }

    @DeleteMapping("/comments/{commentId}")
    public String delete(@PathVariable Long commentId,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        commentService.delete(commentId, userDetails);
        return "commentDeleted";
    }
}