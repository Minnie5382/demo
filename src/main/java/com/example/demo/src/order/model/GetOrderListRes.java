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

public class GetOrderListRes {
    // 주문 id, 주문 날짜, 주문 상태, 가게 이름, 가게 로고, 주문메뉴 개수, 총 결제금액, 가게 오픈 여부
//    ResultSet rs=pstmt.executeQuery();
//    Date date=rs.getTimestamp("reg_date");
    private int orderId;
    private Date orderDate;
    private String orderStatus;
    private String shopName;
    private String logoImgUrl;
    private int menuCount;
    private int totalAmount;
    private String menuName;

    private String openStatus;
}
