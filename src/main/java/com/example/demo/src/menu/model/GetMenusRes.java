package com.example.demo.src.menu.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMenusRes {
    private int menuId;
    private String menuName;
    private String menuDes;
    private int menuPrice;
    private List<GetMenuImgRes> imgs;
    private List<GetOptionRes> options;
}
