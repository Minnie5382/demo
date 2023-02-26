package com.example.demo.src.user;
import com.example.demo.config.BaseException;
import com.example.demo.src.review.model.PostReviewsReq;
import com.example.demo.src.review.model.PostReviewsRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.*;

import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    public PostUserRes createUser(String userTelNum, PostUserReq postUserReq) throws BaseException {
        try {
            int userId = userDao.insertUser(userTelNum, postUserReq);
            return new PostUserRes(userId);

        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void updateUser(int userId, PatchUserReq patchUserReq) throws BaseException {
        try {
//            // validation : 존재하는 유저인지?
//            if(checkUserExists(userId) == 0) {
//                throw new BaseException(USERS_EMPTY_USER_ID);
//            }
            int result = userDao.updateUser(userId, patchUserReq);
            if (result == 0) {
                throw new BaseException(UPDATE_FAIL_USER);
            }

        } catch (Exception exception) {
            logger.error("App - updateUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
//    // 존재하는 유저인지?
//    public int checkUserExists(int userId) throws BaseException{
//        try{
//            return userDao.checkUserExists(userId);
//        } catch (Exception exception){
//            logger.error("App - checkUserExists Provider Error", exception);
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    // 회원 탈퇴 메서드
    public void deleteUser(int userId) throws BaseException {
//        // Validation : 존재하는 유저인지?
//        if (checkUserExists(shopId) == 0) {
//            throw new BaseException(USERS_EMPTY_USER_ID);
//        }
        try {
            int result = userDao.deleteUser(userId);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_REVIEW);
            }

        } catch (Exception exception) {
            logger.error("App - deleteReview Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
