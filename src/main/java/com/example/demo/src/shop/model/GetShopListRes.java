package com.example.demo.src.shop.model;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetShopListRes {
    // 가게id, 가게이름, 가게로고id, 가게로고, 가게 평점, 리뷰 개수, 대표메뉴, 최소주문금액, 포장가능여부, 배달팁, 배달예상시간
    private int shopId;
    private String shopName;
    private String LogoImgUrl;
    private float avgStar;
    private int reviewCount;
    private String repreMenu;
    private int leastPrice;
    private String isPickup;
    private int deliveryTip;
    private int delTimeFrom;
    private int getDelTimeTo;


}
