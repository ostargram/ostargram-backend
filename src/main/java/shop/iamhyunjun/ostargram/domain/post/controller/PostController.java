package shop.iamhyunjun.ostargram.domain.post.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.iamhyunjun.ostargram.domain.post.dto.PostListDto;
import shop.iamhyunjun.ostargram.domain.post.dto.PostResponseDto;
import shop.iamhyunjun.ostargram.domain.post.dto.PostSaveDto;
import shop.iamhyunjun.ostargram.domain.post.dto.PostUpdateDto;
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

    //글 목록 조회
    @GetMapping
    public Result postList() {
        List<PostListDto> postList = postService.findPosts();
        return new Result(postList);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T postList;
    }

    //글 작성
    @PostMapping
    public String write(@Validated @RequestBody PostSaveDto postSaveDto, UserDetailsImpl userDetails) {
        log.info("postSaveDto = " + postSaveDto );
        log.info("postSaveDto.title = " + postSaveDto.getTitle());
        log.info("postSaveDto.content = " + postSaveDto.getContent());
        postService.save(postSaveDto,userDetails);
        return "posted";
    }

    //글 단건 조회
    @GetMapping("/{postId}")
    public PostPage seePost(@PathVariable Long postId) {
        Post post = postService.findPost(postId);
        return new PostPage(new PostResponseDto(post));
    }

    @Data
    @AllArgsConstructor
    static class PostPage<T1> {
        private T1 post;
    }

    //글 수정
    @PatchMapping("/{postId}")
    public String edit(@PathVariable Long postId,
                       @Validated @RequestBody PostUpdateDto postUpdateDto,
                       @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        postService.updatePost(postId, postUpdateDto,userDetails);
        return "edited";
    }

    //글 삭제
    @DeleteMapping("/{postId}")
    public String delete(@PathVariable Long postId,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws IllegalAccessException {
        postService.delete(postId,userDetails);
        return "deleted";
    }
}
