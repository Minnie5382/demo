package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderMenuRes {
    // order Menu :  메뉴id, 주문 메뉴 이름, 메뉴 개수, 주문 메뉴 가격, 옵션id, 주문 옵션이름, 옵션 가격
    private int menuId;
    private String menuName;
    private int menuCount;
    private int menuPrice;
    private int optionId;
    private String optionName;
    private int optionPrice;

}
