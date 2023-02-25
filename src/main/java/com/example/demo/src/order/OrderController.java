package com.example.demo.src.order;

import com.example.demo.src.order.model.*;
//import com.example.demo.src.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders") // http://vici-minn.shop/orders
public class OrderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired // 의존성 주입을 위한 어노테이션. 객체 생성을 자동으로 해준다.
    private final OrderProvider orderProvider;
//    @Autowired
//    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;

    public OrderController(OrderProvider orderProvider, /*OrderService orderService,*/ JwtService jwtService){
        this.orderProvider = orderProvider;
//        this.orderService = orderService;
        this.jwtService = jwtService;
    }


    // 주문내역 조회 메서드
    @ResponseBody
    @GetMapping("") // GET http://127.0.0.1:9000/orders?userId=?
    public BaseResponse<List<GetOrderListRes>> getOrderList(@RequestParam("userId") int userId) {
        try{
            List<GetOrderListRes> getOrderListRes = orderProvider.retrieveOrderList(userId);
            return new BaseResponse<>(getOrderListRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 주문내역 상세 조희 메서드
    @ResponseBody
    @GetMapping("/{orderId}") // GET http://vici-minn.shop:9000/orders/:orderId
    public BaseResponse<GetOrderRes> getOrder (@PathVariable("orderId") int orderId) {
        try{
            GetOrderRes getOrderRes = orderProvider.retrieveOrder(orderId);
            return new BaseResponse<>(getOrderRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 배달현황 조희 메서드
    @ResponseBody
    @GetMapping("/delivery/{orderId}") // GET http://vici-minn.shop:9000/orders/delivery/:orderId
    public BaseResponse<GetDeliveryStatusRes> getDeliveryStatusRes (@PathVariable("orderId") int orderId) {
        try{
            GetDeliveryStatusRes getDeliveryStatusRes = orderProvider.retrieveDelivertStatus(orderId);
            return new BaseResponse<>(getDeliveryStatusRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
