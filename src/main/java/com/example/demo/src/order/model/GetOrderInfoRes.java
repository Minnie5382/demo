package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetOrderInfoRes {
    //order Information : 주문상태, 가게 이름, 주문일시, 주문번호, 주문금액, 배달팁, 총 결제금액, 결제 방법
    private String orderStatus;
    private String shopName;
    private String orderDate;
    private String orderNumber;
    private int price;
    private int deliveryTip;
    private int totalAmount;
    private String payment;
}
