package com.example.demo.src.shop.model;

import java.util.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetShopHomeRes {
    private GetShopInfoRes getShopInfo;
    private List<GetShopMenusRes> getShopMenus;
}
