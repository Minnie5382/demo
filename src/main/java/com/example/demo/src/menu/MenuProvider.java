package com.example.demo.src.menu;

import com.example.demo.config.BaseException;
import com.example.demo.src.menu.model.GetMenuImgRes;
import com.example.demo.src.menu.model.GetMenusRes;
import com.example.demo.src.menu.model.GetOptionRes;
import com.example.demo.src.menu.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class MenuProvider {
    private final MenuDao menuDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MenuProvider(MenuDao menuDao, JwtService jwtService) {
        this.menuDao = menuDao;
        this.jwtService = jwtService;
    }

    // 메뉴 상세보기 메서드
    public List<GetMenusRes> retrieveMenus(int menuId) throws BaseException {
        // Validation : 가게가 존재하는지? -> 일단 보류
//        if(checkShopExists(shopId) == 0)
//        {
//            throw new BaseException(SHOPS_EMPTY_SHOP_ID);
//        }
        try {
            List<GetMenusRes> getMenus = menuDao.selectMenus(menuId);

            return getMenus;

        } catch (Exception exception) {
            logger.error("App - retrieveMenus Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 가게가 존재하는지? 메서드
    public int checkShopExists(int shopId) throws BaseException{
        try{
            return menuDao.checkShopExists(shopId);
        } catch (Exception exception){
            logger.error("App - checkShopExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}

