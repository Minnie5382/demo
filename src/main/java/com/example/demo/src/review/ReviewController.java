package com.example.demo.src.review;

import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.review.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/reviews") // http://vici-minn.shop:9000/reviews
public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;

    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService){
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    // 가게 리뷰 list 불러오기 메서드
    @ResponseBody
    @GetMapping("") // GET http://vici-minn.shop:9000/reviews?shopId=?
    public BaseResponse<List<GetShopReviewsRes>> getShopReviews(@RequestParam("shopId") int shopId) {
        try{
            List<GetShopReviewsRes> getReviewsRes = reviewProvider.retrieveShopReviews(shopId);
            return new BaseResponse<>(getReviewsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 유저 리뷰 List 불러오기 메서드
    @ResponseBody
    @GetMapping("/user/{userId}") // GET http://vici-minn.shop:9000/reviews/user/:userId
    public BaseResponse<List<GetUserReviewsRes>> getUserReviews(@PathVariable("userId") int userId) {
        try{
            List<GetUserReviewsRes> getUserReviewsRes = reviewProvider.retrieveUserReviews(userId);
            return new BaseResponse<>(getUserReviewsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 리뷰 쓰기 메서드
    @ResponseBody
    @PostMapping("") // POST http://vici-minn.shop:9000/reviews
    public BaseResponse<PostReviewsRes> createReviews(@RequestBody PostReviewsReq postReviewsReq) {
        try{
            // 회원용 API
            int userIdByJwt = jwtService.getUserId();
            if (postReviewsReq.getUserId() != userIdByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // validation : 리뷰 내용이 500자 이하인가?
            if(postReviewsReq.getContent().length() > 500) {
                return new BaseResponse<>(BaseResponseStatus.POST_REVIEWS_INVALID_CONTENT);
            }

            PostReviewsRes postReviewsRes = reviewService.createReviews(postReviewsReq.getUserId(), postReviewsReq);
            return new BaseResponse<>(postReviewsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 리뷰 삭제 메서드
    @ResponseBody
    @PatchMapping("/{reviewId}/status") // POST http://vici-minn.shop:9000/reviews/:reviewId/status
    public BaseResponse<String> deleteReview(@PathVariable ("reviewId") int reviewId) {
        try{
            reviewService.deleteReview(reviewId);
            String message = "리뷰가 성공적으로 삭제되었습니다.";

            return new BaseResponse<>(message);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}

