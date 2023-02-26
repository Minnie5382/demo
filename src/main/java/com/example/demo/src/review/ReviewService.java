package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.review.ReviewDao;
import com.example.demo.src.review.ReviewProvider;
import com.example.demo.src.review.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;
    private final ReviewProvider reviewProvider;
    private final JwtService jwtService;


    @Autowired
    public ReviewService(ReviewDao reviewDao, ReviewProvider reviewProvider, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.reviewProvider = reviewProvider;
        this.jwtService = jwtService;

    }
    // 리뷰 쓰기 메서드

    public PostReviewsRes createReviews(int userId, PostReviewsReq postReviewsReq) throws BaseException {
        try {
            int reviewId = reviewDao.insertReviews(userId, postReviewsReq);
            if(postReviewsReq.getImgs().size() < 1) {
                for (int i = 0; i < postReviewsReq.getImgs().size(); i++) {
                    reviewDao.insertReviewImgs(reviewId, postReviewsReq.getImgs().get(i));
                }
            }
            return new PostReviewsRes(reviewId);

        } catch (Exception exception) {
            logger.error("App - createReviews Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 리뷰 삭제 메서드
    public void deleteReview(int reviewId) throws BaseException {
        // Validation : 존재하는 리뷰인지?
        if (checkReviewExists(reviewId) == 0) {
            throw new BaseException(REVIEWS_EMPTY_REVIEW_ID);
        }
        try {
            int result = reviewDao.deleteReview(reviewId);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_REVIEW);
            }

        } catch (Exception exception) {
            logger.error("App - deleteReview Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 존재하는 리뷰인지?
    public int checkReviewExists(int reviewId) throws BaseException {
        try {
            return reviewDao.checkReviewExists(reviewId);
        } catch (Exception exception) {
            logger.error("App - checkReviewExists Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
