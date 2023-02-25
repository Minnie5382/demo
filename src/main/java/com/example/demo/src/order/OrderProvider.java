package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.lang.*;

import static com.example.demo.config.BaseResponseStatus.*;


@Service
public class OrderProvider {
    private final OrderDao orderDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderProvider(OrderDao orderDao, JwtService jwtService) {
        this.orderDao = orderDao;
        this.jwtService = jwtService;
    }

    // 주문내역 List 불러오기 메서드
    public List<GetOrderListRes> retrieveOrderList(int userId) throws BaseException {
        // validation : 존재하는 유저인지?
        if(checkUserExists(userId) == 0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        try{
            List<GetOrderListRes> getOrderListRes = orderDao.getOrderList(userId);
            return getOrderListRes;
        } catch (Exception exception){
            logger.error("App - retrieveOrderList Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
    // 존재하는 유저인지?
    public int checkUserExists(int userId) throws BaseException{
        try{
            return orderDao.checkUserExists(userId);
        } catch (Exception exception){
            logger.error("App - checkUserExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 주문id가 들어오면 해당 주문건을 주문한 유저가 맞는지?
    public int checkUserMatch(int orderId) throws BaseException{
        try{
            return orderDao.checkUserMatch(orderId);
        } catch (Exception exception){
            logger.error("App - checkUserMatch Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 존재하는 주문건인지?
    public int checkOrderExists(int orderId) throws BaseException{
        try{
            return orderDao.checkOrderExists(orderId);
        } catch (Exception exception){
            logger.error("App - checkOrderExists Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 주문내역 상세 불러오기 메서드
    public GetOrderRes retrieveOrder(int orderId) throws BaseException {
//        // validation : 존재하는 유저인지?
//        if(checkUserExists(userId) == 0){
//            throw new BaseException(USERS_EMPTY_USER_ID);
//        }
        // validation : 해당 주문건의 주문자가 맞는지?
//        if(checkUserMatch(orderId) == userId) {
//            throw new BaseException();
//
//        }
        // validation : 존재하는 주문건인지?
        if(checkOrderExists(orderId) == 0) {
            throw new BaseException(ORDERS_EMPTY_ORDER_ID);
        }
        try{
            GetOrderInfoRes getOrderInfoRes = orderDao.getOrderInfo(orderId);
            List<GetOrderMenuRes> getOrderMenuRes = orderDao.getOrderMenu(orderId);
            GetOrderDeliveryRes getOrderDeliveryRes = orderDao.getOrderDelivery(orderId);

            GetOrderRes getOrderRes = new GetOrderRes(orderId, getOrderInfoRes, getOrderDeliveryRes,getOrderMenuRes);
            return getOrderRes;
        } catch (Exception exception){
            logger.error("App - retrieveOrder Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 배달현황 불러오기 메서드
    public GetDeliveryStatusRes retrieveDelivertStatus(int orderId) throws BaseException {
        // validation : 존재하는 주문건인지?
        if(checkOrderExists(orderId) == 0){
            throw new BaseException(ORDERS_EMPTY_ORDER_ID);
        }
        try{
            GetDeliveryStatusRes getDeliveryStatusRes = orderDao.getDeliveryStatus(orderId);
            return getDeliveryStatusRes;
        } catch (Exception exception){
            logger.error("App - retrieveDeliveryStatus Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
