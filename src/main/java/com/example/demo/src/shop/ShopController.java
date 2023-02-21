package com.example.demo.src.shop;

import com.example.demo.src.shop.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/shops") // http://127.0.0.1:9000/shops
public class ShopController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final ShopProvider shopProvider;
    @Autowired
    private final ShopService shopService;
    @Autowired
    private final JwtService jwtService;

    public ShopController(ShopProvider shopProvider, ShopService shopService, JwtService jwtService){
        this.shopProvider = shopProvider;
        this.shopService = shopService;
        this.jwtService = jwtService;
    }

    // 가게 첫화면 불러오기 메서드
    @ResponseBody
    @GetMapping("/{shopId}") // GET http://127.0.0.1:9000/shops/:shopId
    public BaseResponse<GetShopHomeRes> getShopHome(@PathVariable("shopId") int shopId) {
        try{
            GetShopHomeRes GetShopHomeRes = shopProvider.retrieveShopHome(shopId);
            return new BaseResponse<>(GetShopHomeRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
