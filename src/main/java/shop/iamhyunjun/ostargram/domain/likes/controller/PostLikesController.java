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
import shop.iamhyunjun.ostargram.domain.likes.dto.LikeMessage;
import shop.iamhyunjun.ostargram.domain.likes.dto.LikeResponse;
import shop.iamhyunjun.ostargram.domain.likes.dto.LikeResponseMessage;
import shop.iamhyunjun.ostargram.domain.likes.entity.PostLikes;
import shop.iamhyunjun.ostargram.domain.likes.service.PostLikesService;
import shop.iamhyunjun.ostargram.security.customfilter.UserDetailsImpl;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static shop.iamhyunjun.ostargram.domain.likes.dto.LikeMessage.LIKE_CANCEL_MESSAGE;
import static shop.iamhyunjun.ostargram.domain.likes.dto.LikeMessage.LIKE_MESSAGE;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostLikesController {

    private final PostLikesService postLikesService;

    @PostMapping("/posts/{postsId}/likes")
    public ResponseEntity<LikeResponseMessage> likesPost(@PathVariable Long postsId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostLikes postLikes = postLikesService.execute(postsId, userDetails.getUser());

        if (postLikes.isLiked()) {
            return sendLikeResponseMessage(LIKE_MESSAGE);
        }
        return sendLikeResponseMessage(LIKE_CANCEL_MESSAGE);
    }

    private ResponseEntity<LikeResponseMessage> sendLikeResponseMessage(LikeMessage likeMessage) {
        LikeResponseMessage responseMessage = new LikeResponseMessage(CREATED.value(), likeMessage.getMessage());
        return new ResponseEntity<>(responseMessage, CREATED);
    }

    @GetMapping("/posts/{postsId}/likes")
    public ResponseEntity<Object> checkLikes(@PathVariable Long postsId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Boolean isLiked = postLikesService.checkLikes(postsId, userDetails.getUser());

        LikeResponse response = new LikeResponse(OK.value(), isLiked);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}
