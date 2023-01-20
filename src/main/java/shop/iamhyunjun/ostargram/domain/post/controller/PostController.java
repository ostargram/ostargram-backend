package shop.iamhyunjun.ostargram.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.file.service.ImageService;
import shop.iamhyunjun.ostargram.domain.post.dto.*;
import shop.iamhyunjun.ostargram.domain.post.service.PostService;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final ImageService imageService;

    @Value("${default.image}")
    private String defaultImage;

    //글 목록 조회
    @GetMapping
    public ResponseEntity<PostDataDto> postList() {
        List<PostListDto> postList = postService.findPosts();
        PostDataDto postDataDto = new PostDataDto(200, "글 목록 조회 완료", postList);
        return new ResponseEntity<>(postDataDto, HttpStatus.OK);
    }

    //글 작성
    @PostMapping                   //ModelAttribute로 PostSaveDto로 제목, 내용, 이미지 파일을 받음
    public ResponseEntity<PostMessageDto> write(@Validated @ModelAttribute PostSaveDto postSaveDto,
                                                @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 첨부 이미지 없을 시 기본 이미지 저장
        String saveImage;
        if (postSaveDto.getImage() == null) {
            saveImage = defaultImage;
            postService.save(postSaveDto, userDetails, saveImage);
            PostMessageDto postMessageDto = new PostMessageDto(201, "글 작성 완료");
            return new ResponseEntity<>(postMessageDto, HttpStatus.CREATED);
        }
        saveImage = imageService.uploadFile(postSaveDto.getImage());
        postService.save(postSaveDto,userDetails,saveImage);
        PostMessageDto postMessageDto = new PostMessageDto(201, "글 작성 완료");
        return new ResponseEntity<>(postMessageDto, HttpStatus.CREATED);
    }

    //글 단건 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostDataDto> seePost(@PathVariable Long postId) {
        PostResponseDto foundPost = postService.findPost(postId);
        PostDataDto postDataDto = new PostDataDto(200, "글 조회 완료", foundPost);
        return new ResponseEntity<>(postDataDto, HttpStatus.OK);
    }

    //글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<PostMessageDto> edit(@PathVariable Long postId,
                       @Validated @RequestBody PostUpdateDto postUpdateDto,
                       @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        postService.updatePost(postId, postUpdateDto,userDetails);
        PostMessageDto postMessageDto = new PostMessageDto(200, "글 수정 완료");
        return new ResponseEntity<>(postMessageDto,HttpStatus.OK);
    }

    //글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostMessageDto> delete(@PathVariable Long postId,
                         @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        postService.delete(postId,userDetails);
        PostMessageDto postMessageDto = new PostMessageDto(200, "글 삭제 완료");
        return new ResponseEntity<>(postMessageDto,HttpStatus.OK);
    }
}
