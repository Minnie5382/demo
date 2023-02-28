package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.src.user.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

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

    // 로그인 메서드
    public PostLoginRes login(PostLoginReq postLoginReq) throws BaseException{

        User user = userDao.getPwdByEmail(postLoginReq); // DB에서 받아온 옳은 비밀번호 -> User(userId, pwd)
        String encryptPwd;

        try {
            encryptPwd = new SHA256().encrypt(postLoginReq.getPwd()); // 유저가 입력한 비밀번호
        } catch (Exception exception){
            logger.error("App - login Service Error", exception);
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        if (userProvider.checkEmailExists(postLoginReq.getEmail()) == 1) {
            if (new SHA256().encrypt(user.getPwd()).equals(encryptPwd)) {
                // user.getPwd()는 암호화가 되지 않은 변수인데, encryptPwd는 암호화가 된 변수로 보임
                // ** 참고로, DB에 저장되어 있는 암호화된 비번을 decrypt해서 비교해야 함
                int userId = user.getUserId();
                String jwt = jwtService.createJwt(userId);
                return new PostLoginRes(userId, jwt);
            } else throw new BaseException(FAILED_TO_LOGIN);
        } else throw new BaseException(FAILED_TO_LOGIN);


    }

    // 회원가입 메서드
    public PostUserRes signUp(PostUserReq postUserReq) throws BaseException {
        String encryptPwd; // 회원가입 시 유저가 설정하는 비밀번호를 암호화한 것
        try {
            // validation : 이미 가입된 이메일인지?
            if(userProvider.checkEmailExists(postUserReq.getEmail()) == 1)
                throw new BaseException(POST_USERS_EXISTS_EMAIL);
            // validation : 이미 가입된 휴대폰 번호인지?
            if(userProvider.checkTelNumExists(postUserReq.getUserTelNum()) == 1)
                throw new BaseException(POST_USERS_EXISTS_TELNUM);
            // validation : 이미 가입된 닉네임인지?
            if(userProvider.checkNicknameExists(postUserReq.getNickname()) == 1)
                throw new BaseException(POST_USERS_EXISTS_NICKNAME);

            encryptPwd = new SHA256().encrypt(postUserReq.getPwd()); // 유저가 입력한 비밀번호
            int userId = userDao.insertUser(postUserReq, encryptPwd);
            String jwt = jwtService.createJwt(userId);

            return new PostUserRes(userId, jwt);
        } catch (Exception exception) {
            logger.error("App - login Service Error", exception);
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
    }

    // 회원 탈퇴 메서드 : 수정 필요
    public String deleteUser(int userId) throws BaseException {
        try {
            int result = userDao.patchUserStatus(userId);
            if (result == 1) {
                String successMes = "탈퇴가 완료되었습니다.";
                return successMes;
            } else {
                String failMes = "정상적으로 탈퇴되지 않았습니다.";
                return failMes;
            }
        } catch (Exception exception) {
            logger.error("App - deleteUser Service Error", exception);
            throw new BaseException(DELETE_FAIL_USER);
        }
    }

    // 회원 정보 수정 메서드 (비밀번호 제외)
    public PatchUserRes updateUser(int userId, PatchUserReq patchUserReq) throws BaseException {
        // validation : 이메일 형식 확인
        if(!isRegexEmail(patchUserReq.getEmail()))
            throw new BaseException(POST_USERS_INVALID_EMAIL);
        try {
            PatchUserRes patchUserRes = userDao.updateUser(userId, patchUserReq);

            return patchUserRes;
        } catch (Exception exception) {
            logger.error("App - updateUser Service Error", exception);
            throw new BaseException(UPDATE_FAIL_USER);
        }
    }




}
