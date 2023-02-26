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
    public List<GetShopReviewsRes> retrieveShopReviews(int shopId) throws BaseException {
        // Validation : 가게가 존재하는지?
        if(checkShopExists(shopId) == 0){
            throw new BaseException(SHOPS_EMPTY_SHOP_ID);
        }
        try {
            List<GetShopReviewsRes> getShopReviewsRes = reviewDao.getShopReviews(shopId);
            return getShopReviewsRes;

        } catch (Exception exception) {
            logger.error("App - retrieveShopReviews Provider Error", exception);
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

    // 유저 리뷰 목록 불러오기 메서드
    public List<GetUserReviewsRes> retrieveUserReviews(int userId) throws BaseException {
        // Validation : 유저가 존재하는지?
        if(checkUserExists(userId) == 0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        try {
            List<GetUserReviewsRes> getUserReviewsRes = reviewDao.getUserReviews(userId);
            return getUserReviewsRes;

        } catch (Exception exception) {
            logger.error("App - retrieveUserReviews Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 존재하는 유저인지?
    public int checkUserExists(int userId) throws BaseException{
        try{
            return reviewDao.checkUserExists(userId);
        } catch (Exception exception){
            logger.error("App - checkUserExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
