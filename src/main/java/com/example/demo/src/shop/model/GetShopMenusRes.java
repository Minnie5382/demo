package com.example.demo.src.shop.model;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetShopMenusRes {
    private int menuId;
    private String menuGroup;
    private String name;
    private int Price;
    private String menuImgUrl;
    private String isRepresent;
}
