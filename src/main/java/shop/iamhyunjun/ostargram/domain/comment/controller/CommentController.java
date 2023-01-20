package shop.iamhyunjun.ostargram.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentSaveDto;
import shop.iamhyunjun.ostargram.domain.comment.dto.CommentUpdateDto;
import shop.iamhyunjun.ostargram.domain.comment.service.CommentService;
import shop.iamhyunjun.ostargram.domain.post.dto.PostMessageDto;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<PostMessageDto> comment(@PathVariable Long postId,
                          @RequestBody CommentSaveDto commentSaveDto) throws IllegalAccessException {
        commentService.commentSave(postId,commentSaveDto);
        PostMessageDto postMessageDto = new PostMessageDto(201, "댓글 작성 완료");
        return new ResponseEntity<>(postMessageDto, HttpStatus.CREATED);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<PostMessageDto> edit(@PathVariable Long commentId,
                       @Validated @RequestBody CommentUpdateDto commentUpdateDto,
                       @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        commentService.updateComment(commentId, commentUpdateDto, userDetails);
        PostMessageDto postMessageDto = new PostMessageDto(200, "댓글 수정 완료");
        return new ResponseEntity<>(postMessageDto,HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<PostMessageDto> delete(@PathVariable Long commentId,
                         @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        commentService.delete(commentId, userDetails);
        PostMessageDto postMessageDto = new PostMessageDto(200, "댓글 삭제 완료");
        return new ResponseEntity<>(postMessageDto,HttpStatus.OK);
    }
}