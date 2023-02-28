package com.example.demo.src.dib;

import com.example.demo.config.BaseException;
import com.example.demo.src.dib.DibDao;
import com.example.demo.src.dib.DibProvider;
import com.example.demo.src.dib.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class DibService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DibDao dibDao;
    private final DibProvider dibProvider;
    private final JwtService jwtService;


    @Autowired
    public DibService(DibDao dibDao, DibProvider dibProvider, JwtService jwtService) {
        this.dibDao = dibDao;
        this.dibProvider = dibProvider;
        this.jwtService = jwtService;

    }


    // 찜 누르기 메서드
    public PostDibRes likeShop(int userId, int shopId) throws BaseException {
        try {
                PostDibRes postDibRes = dibDao.retrieveDib(userId, shopId);
                return postDibRes;
        } catch (Exception exception) {
            logger.error("App - likeShop Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
