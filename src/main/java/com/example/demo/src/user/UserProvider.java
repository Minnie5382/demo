package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;


@Service
public class UserProvider {
    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    /**
     * 밑에 나오는 세 개 함수 다 에러남.
     * Dao 단에서 에러나는 것 같은데 아직 해결을 못했음ㅠ
     * expected 1 result 0 라고함
     */
    // 존재하는 이메일인지?
    public int checkEmailExists(String email) throws BaseException {
        try {
            return userDao.checkEmailExists(email);
        } catch (Exception exception){
            logger.error("App - checkEmailExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 존재하는 휴대폰 번호인지?
    public int checkTelNumExists(String telNum) throws BaseException {
        try {
            return userDao.checkTelNumExists(telNum);
        } catch (Exception exception){
            logger.error("App - checkTelNumExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 존재하는 닉네임인지?
    public int checkNicknameExists(String nickname) throws BaseException{
        try {
            return userDao.checkNicknameExists(nickname);
        } catch (Exception exception){
            logger.error("App - checkNicknameExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

