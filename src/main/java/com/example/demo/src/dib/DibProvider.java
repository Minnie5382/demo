package com.example.demo.src.dib;

import com.example.demo.config.BaseException;
import com.example.demo.src.dib.DibDao;
import com.example.demo.src.dib.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class DibProvider {
    private final DibDao dibDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DibProvider(DibDao dibDao, JwtService jwtService) {
        this.dibDao = dibDao;
        this.jwtService = jwtService;
    }
/*
    // 메뉴 상세보기 메서드
    public List<GetMenusRes> retrieveMenus(int menuId) throws BaseException {
        if(checkMenuExists(menuId) == 0){
            throw new BaseException(MENUS_EMPTY_MENU_ID);
        }
        else if(checkShopOfMenuExists(menuId) == 0){
            throw new BaseException(SHOPS_EMPTY_SHOP_ID);
        }
        try {
            List<GetMenusRes> getMenus = menuDao.selectMenus(menuId);

            return getMenus;

        } catch (Exception exception) {
            logger.error("App - retrieveMenus Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 menuId에 해당하는 가게가 존재하는지?
    public int checkShopOfMenuExists(int menuId) throws BaseException{
        try{
            return menuDao.checkShopOfMenuExists(menuId);
        } catch (Exception exception){
            logger.error("App - checkShopOfMenuExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 menuId에 해당하는 메뉴가 존재하는지?
    public int checkMenuExists(int menuId) throws BaseException{
        try{
            return menuDao.checkMenuExists(menuId);
        } catch (Exception exception){
            logger.error("App - checkMenuExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

 */

}

