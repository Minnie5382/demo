// 가게 정보를 보여주는 클래스
package com.example.demo.src.shop.model;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetShopInfoRes {
    private int shopId;
    private String shopName;
    private String shopHomeImgUrl;
    private float avgStar;
    private int reviewCount;
    private int ownerComCount;
    private int dibCount;
    private int leastPrice;
    private int delTimeFrom;
    private int delTimeTo;
    private int deliveryTip;
}
