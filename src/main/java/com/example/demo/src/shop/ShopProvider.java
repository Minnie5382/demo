package com.example.demo.src.shop;

import com.example.demo.config.BaseException;
import com.example.demo.src.shop.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.*;

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

    // 카테고리 별 가게 목록 불러오기 메서드
    public List<GetShopListRes> retrieveShopList(String category) throws BaseException {
        // Validation : 카테고리가 존재하는지?
        if(checkCategoryExists(category) == false){
            throw new BaseException(CATEGORIES_EMPTY_CATEGORY);
        }
        try {
            List<GetShopListRes> getShopListRes = shopDao.getShopList(category);
            return getShopListRes;

        } catch (Exception exception) {
            logger.error("App - retrieveShopList Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }



    // 카테고리가 존재하는지? 메서드
    public boolean checkCategoryExists(String category) throws BaseException{
        ArrayList<String> categoryList = new ArrayList<>(Arrays.asList(
                "1인분", "족발보쌈","찜탕찌개","돈까스회일식","피자",
                "고기구이","야식","양식","치킨","중식",
                "아시안","백반죽국수","도시락","분식","카페디저트",
                "패스트푸드"));
        try{
            boolean categoryExists = categoryList.contains(category);
            return categoryExists;
        } catch (Exception exception){
            logger.error("App - checkCategoryExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
