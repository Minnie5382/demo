package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDeliveryStatusRes {
    //  주문 id, 주문 상태, 도착예상시간, 남은 시간, 배달주소, 가게 주소
    private int orderId;
    private String orderStatus;
    private String arriveTime; // 도착예정시간
    private int leftTime; // 남은시간(분)
    private String address;
    private String shopAddress;
}
