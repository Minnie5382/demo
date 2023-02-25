package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderRes {
    private int orderId;
    private GetOrderInfoRes Info;
    private GetOrderDeliveryRes delInfo;
    private List<GetOrderMenuRes> menus;
}
