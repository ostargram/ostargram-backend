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

    public List<PostResponseDto> findPosts() {
        List<PostResponseDto> postList = new ArrayList<>();
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        for (Post post : posts) {
            postList.add(new PostResponseDto(post));
        }
        return postList;
    }

    public Post findPost(Long postId) {
        Post foundPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
        return foundPost;
    }

    @Transactional
    public Post save(PostSaveDto postSaveDto) {
        Post savePost = new Post(postSaveDto);
        postRepository.save(savePost);
        return savePost;
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateDto postUpdateDto, UserDetailsImpl userDetails) {
        Post foundPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
        if (userDetails.getUser().getId().equals(foundPost.getUser().getId())) {
            foundPost.update(postUpdateDto);
        } else throw new IllegalAccessError();
    }

    @Transactional
    public void delete(Long postId,UserDetailsImpl userDetails) {

        Post foundPost = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );
        if (userDetails.getUser().getId().equals(foundPost.getUser().getId())){
            postRepository.deleteById(postId);
        } else throw new IllegalAccessError();

    }



}