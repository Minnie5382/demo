package com.example.demo.src.shop;

import com.example.demo.config.BaseException;
import com.example.demo.src.shop.ShopDao;
import com.example.demo.src.shop.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class ShopProvider {

    private final ShopDao shopDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ShopProvider(ShopDao shopDao, JwtService jwtService) {
        this.shopDao = shopDao;
        this.jwtService = jwtService;
    }

    // 가게 첫화면 불러오기 메서드
    public GetShopHomeRes retrieveShopHome(int shopId) throws BaseException {
        // Validation : 가게가 존재하는지?
        if(checkShopExists(shopId) == 0){
            throw new BaseException(SHOPS_EMPTY_SHOP_ID);
        }
        try {
            GetShopInfoRes getShopInfo = shopDao.selectShopInfo(shopId);
            List<GetShopMenusRes> getShopMenus = shopDao.selectShopMenus(shopId);

            GetShopHomeRes getShopHomeRes = new GetShopHomeRes(getShopInfo, getShopMenus);
            return getShopHomeRes;

        } catch (Exception exception) {
            logger.error("App - retrieveShopHome Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 가게가 존재하는지? 메서드
    public int checkShopExists(int shopId) throws BaseException{
        try{
            return shopDao.checkShopExists(shopId);
        } catch (Exception exception){
            logger.error("App - checkShopExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
