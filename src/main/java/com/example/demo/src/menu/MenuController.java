package com.example.demo.src.menu;

import com.example.demo.src.menu.MenuProvider;
//import com.example.demo.src.menu.MenuService;
import com.example.demo.src.menu.model.*;
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
@RequestMapping("/menus") // http://127.0.0.1:9000/menus
public class MenuController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final MenuProvider menuProvider;
    //@Autowired
    //private final MenuService menuService;
    @Autowired
    private final JwtService jwtService;

    public MenuController(MenuProvider menuProvider, /*MenuService menuService,*/ JwtService jwtService){
        this.menuProvider = menuProvider;
        //this.menuService = menuService;
        this.jwtService = jwtService;
    }

    // 메뉴 상세 페이지 불러오기 메서드
    @ResponseBody
    @GetMapping("/{menuId}") // GET http://127.0.0.1:9000/menus/:menuId
    public BaseResponse<List<GetMenusRes>> getMenus(@PathVariable int menuId) {
        try{
            List<GetMenusRes> getMenusRes = menuProvider.retrieveMenus(menuId);
            return new BaseResponse<>(getMenusRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}

