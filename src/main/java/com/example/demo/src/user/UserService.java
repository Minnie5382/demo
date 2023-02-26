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

    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        try {
            int userId = userDao.insertUser(postUserReq);
            return new PostUserRes(userId);

        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
