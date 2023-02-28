package com.example.demo.src.dib;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.dib.DibProvider;

import com.example.demo.src.dib.model.PostDibRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/dibs") // http://127.0.0.1:9000/dibs
public class DibController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final DibProvider dibProvider;
    @Autowired
    private final DibService dibService;
    @Autowired
    private final JwtService jwtService;

    public DibController(DibProvider dibProvider, DibService dibService, JwtService jwtService){
        this.dibProvider = dibProvider;
        this.dibService = dibService;
        this.jwtService = jwtService;
    }

    // 찜 누르기 메서드
    @ResponseBody
    @PostMapping("") // GET http://vici-minn.shop/dibs?userId&shopId
    public BaseResponse<PostDibRes> getMenus(@RequestParam("userId") int userId, @RequestParam("shopId") int shopId) throws BaseException {
        // 회원용 API
        int userIdByJwt = jwtService.getUserId();
        if (userId != userIdByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        try{
            PostDibRes postDibRes = dibService.likeShop(userId, shopId);
            return new BaseResponse<>(postDibRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}

