package shop.iamhyunjun.ostargram.domain.likes.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.iamhyunjun.ostargram.domain.likes.dto.ClickLikeButtonResponse;
import shop.iamhyunjun.ostargram.domain.likes.dto.IsLikeResponse;
import shop.iamhyunjun.ostargram.domain.likes.dto.LikeMessageEnum;
import shop.iamhyunjun.ostargram.domain.likes.entity.PostLikes;
import shop.iamhyunjun.ostargram.domain.likes.service.PostLikesService;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static shop.iamhyunjun.ostargram.domain.likes.dto.LikeMessageEnum.LIKE_CANCEL_MESSAGE;
import static shop.iamhyunjun.ostargram.domain.likes.dto.LikeMessageEnum.LIKE_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikesService postLikesService;

    // 좋아요 버튼 누르기
    @PostMapping("/posts/{postsId}/likes")
    public ResponseEntity<ClickLikeButtonResponse> likesPost(@PathVariable Long postsId,
                                                             @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostLikes postLikes = postLikesService.clickLikesButton(postsId, userDetails.getUser());

        if (postLikes.isLiked()) {
            return sendLikeResponseMessage(LIKE_MESSAGE);
        }
        return sendLikeResponseMessage(LIKE_CANCEL_MESSAGE);
    }

    private ResponseEntity<ClickLikeButtonResponse> sendLikeResponseMessage(LikeMessageEnum likeMessage) {
        ClickLikeButtonResponse responseMessage = new ClickLikeButtonResponse(CREATED.value(), likeMessage.getMessage());
        return new ResponseEntity<>(responseMessage, CREATED);
    }

    // 좋아요 여부 확인
    @GetMapping("/posts/{postsId}/likes")
    public ResponseEntity<Object> checkLikes(@PathVariable Long postsId,
                                             @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Boolean isLiked = postLikesService.checkLikes(postsId, userDetails.getUser());

        IsLikeResponse response = new IsLikeResponse(OK.value(), isLiked);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
