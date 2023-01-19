package shop.iamhyunjun.ostargram.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.file.service.ImageService;
import shop.iamhyunjun.ostargram.domain.post.dto.*;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;
import shop.iamhyunjun.ostargram.domain.post.service.PostService;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final ImageService imageService;

    //글 목록 조회
    @GetMapping
    public ResponseEntity<PostDataDto> postList() {
        List<PostListDto> postList = postService.findPosts();
        PostDataDto postDataDto = new PostDataDto(200, "글 목록 조회 성공", postList);
        return new ResponseEntity<>(postDataDto, HttpStatus.OK);
    }

    //글 작성
    @PostMapping
    public ResponseEntity<PostMessageDto> write(@Validated @ModelAttribute PostSaveDto postSaveDto,
                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String saveImage = imageService.uploadFile(postSaveDto.getImage());
        postService.save(postSaveDto,userDetails,saveImage);
        PostMessageDto postMessageDto = new PostMessageDto(201, "글 작성 완료");
        return new ResponseEntity<>(postMessageDto, HttpStatus.CREATED);
    }

    //글 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostDataDto> seePost(@PathVariable Long postId) {
        Post post = postService.findPost(postId);
        PostDataDto postDataDto = new PostDataDto(200, "글 조회 성공", new PostResponseDto(post));
        return new ResponseEntity<>(postDataDto, HttpStatus.OK);
    }

    //글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostMessageDto> edit(@PathVariable Long postId,
                       @Validated @RequestBody PostUpdateDto postUpdateDto,
                       @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        postService.updatePost(postId, postUpdateDto,userDetails);
        PostMessageDto postMessageDto = new PostMessageDto(200, "글 수정 완료");
        return new ResponseEntity<>(postMessageDto,HttpStatus.OK);
    }

    //글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostMessageDto> delete(@PathVariable Long postId,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        postService.delete(postId,userDetails);
        PostMessageDto postMessageDto = new PostMessageDto(200, "글 삭제 완료");
        return new ResponseEntity<>(postMessageDto,HttpStatus.OK);
    }
}
