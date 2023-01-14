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

//    @GetMapping("/{postId}/comments")
//    public ResponseEntity commentList(@PathVariable Long postId) {
//        List<CommentResponseDto> commentList = commentService.findComments(postId);
//        DTO dto = new DTO(commentList);
//        return new ResponseEntity(dto, HttpStatus.OK);
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class DTO<T> {
//        private T data;
//    }

    @PostMapping("/posts/{postId}/comments")
    public String comment(@PathVariable Long postId, @RequestBody CommentSaveDto commentSaveDto,
                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.commentSave(postId,commentSaveDto,userDetails);
        return "commentSaved";
    }

    @PostMapping("/comments/{commentId}")
    public String edit(@PathVariable Long commentId,
                       @Validated @RequestBody CommentUpdateDto commentUpdateDto) {
        commentService.updateComment(commentId, commentUpdateDto);
        return "commentEdited";
    }

    @DeleteMapping({"/comments/{commentId}"})
    public String delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return "commentDeleted";
    }
}