package com.example.demo.src.review;

//import com.example.demo.src.review.ShopService;
import com.example.demo.src.review.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews") // http://vici-minn.shop:9000/reviews
public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final ReviewProvider reviewProvider;
//    @Autowired
//    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;

    public ReviewController(ReviewProvider reviewProvider, /*ReviewService reviewService,*/ JwtService jwtService){
        this.reviewProvider = reviewProvider;
        //this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    // 가게 리뷰 list 불러오기 메서드
    @ResponseBody
    @GetMapping("") // GET http://vici-minn.shop:9000/reviews?shopId=?
    public BaseResponse<List<GetShopReviewsRes>> getShopReviews(@RequestParam("shopId") int shopId) {
        try{
            List<GetShopReviewsRes> getReviewsRes = reviewProvider.retrieveReviews(shopId);
            return new BaseResponse<>(getReviewsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
