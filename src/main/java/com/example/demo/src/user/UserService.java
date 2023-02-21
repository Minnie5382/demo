// 비즈니스 로직을 처리하는 파일 (Create, Update, Delete) : R이 필요한 경우엔 Provider에 구성되어 있는 것을 Service에서 사용하면 된다.
package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
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

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        // 이메일 중복 확인
        if(userProvider.checkEmail(postUserReq.getEmail()) == 1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL); // 이미 가입된 이메일입니다.
        }
        // 휴대폰 번호 중복 확인
        if(userProvider.checkUserTelNum(postUserReq.getUserTelNum()) == 1){
            throw new BaseException(POST_USERS_EXISTS_TELNUM); // 이미 가입된 휴대폰 번호입니다.
        }
        try{
            int userId = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userId);
            return new PostUserRes(jwt,userId);
        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
//
//    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
//        try{
//            int result = userDao.modifyUserName(patchUserReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USERNAME);
//            }
//        } catch(Exception exception){
//            logger.error("App - modifyUserName Service Error", exception);
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
