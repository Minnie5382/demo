package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderDeliveryRes {
//  order Delivery : 배달주소, 고객 전화번호, 사장님께, 라이더님께
    private String address;
    private String userTelNum;
    private String requestToOwner;
    private String requestToRider;
}
