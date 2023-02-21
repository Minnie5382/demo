// 가게 메뉴판을 보여주는 클래스
package com.example.demo.src.shop.model;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetShopMenusRes {
    private int menuId;
    private String menuGroup;
    private String name;
    private int price;
    private String menuImgUrl;
    private String isRepresent;

}
