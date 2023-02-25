package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.*;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class ReviewProvider {

    private final ReviewDao reviewDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReviewProvider(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.jwtService = jwtService;
    }

    // 가게 리뷰 목록 불러오기 메서드
    public List<GetShopReviewsRes> retrieveReviews(int shopId) throws BaseException {
        // Validation : 가게가 존재하는지?
        if(checkShopExists(shopId) == 0){
            throw new BaseException(SHOPS_EMPTY_SHOP_ID);
        }
        try {
            List<GetShopReviewsRes> getReviewsRes = reviewDao.getShopReviews(shopId);
            return getReviewsRes;

        } catch (Exception exception) {
            logger.error("App - retrieveReviews Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 존재하는 가게인지?
    public int checkShopExists(int shopId) throws BaseException{
        try{
            return reviewDao.checkShopExists(shopId);
        } catch (Exception exception){
            logger.error("App - checkShopExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
