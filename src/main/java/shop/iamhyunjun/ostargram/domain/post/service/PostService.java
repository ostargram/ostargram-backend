package shop.iamhyunjun.ostargram.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.iamhyunjun.ostargram.domain.post.dto.PostResponseDto;
import shop.iamhyunjun.ostargram.domain.post.dto.PostSaveDto;
import shop.iamhyunjun.ostargram.domain.post.dto.PostUpdateDto;
import shop.iamhyunjun.ostargram.domain.post.entity.Post;
import shop.iamhyunjun.ostargram.domain.post.repositiory.PostRepository;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    //글 목록 조회
    public List<PostResponseDto> findPosts() {
        List<PostResponseDto> postList = new ArrayList<>();
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        for (Post post : posts) {
            postList.add(new PostResponseDto(post));
        }
        return postList;
    }

    //글 단건 조회
    public Post findPost(Long postId) {
        Post foundPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
        return foundPost;
    }

    //글 작성
    @Transactional
    public Post save(PostSaveDto postSaveDto, UserDetailsImpl userDetails) {
        Post savePost = new Post(postSaveDto, userDetails.getUser());
        postRepository.save(savePost);
        return savePost;
    }

    //글 수정
    @Transactional
    public void updatePost(Long postId, PostUpdateDto postUpdateDto, UserDetailsImpl userDetails) throws IllegalAccessException {
        Post foundPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
        log.info("foundPost = " + foundPost.getTitle());
        log.info("userDetails = " + userDetails.getUser().getId());
        if (userDetails.getUser().getId().equals(foundPost.getCreatedBy())) {
            foundPost.update(postUpdateDto);
        } else throw new IllegalAccessException();
    }

    //글 삭제
    @Transactional
    public void delete(Long postId,UserDetailsImpl userDetails) throws IllegalAccessException {

        Post foundPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
        if (userDetails.getUser().getId().equals(foundPost.getCreatedBy())){
            postRepository.deleteById(postId);
        } else throw new IllegalAccessException();

    }

}