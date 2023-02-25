package com.example.demo.src.order;

import com.example.demo.src.order.model.*;
import com.example.demo.src.shop.model.GetShopMenusRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.*;
import java.sql.ResultSet;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 특정 가게의 메뉴판 불러오기 메서드
    public List<GetShopMenusRes> selectShopMenus(int shopId){
        String selectShopMenusQuery = "" +
                "select Menu.menuId, menuGroup, name, price, menuImgUrl, isRepresent\t\n" +
                "from Menu left join MenuImage on Menu.menuId=MenuImage.menuId\t\n" +
                "where shopId=?;\t";
        int selectShopMenusParams = shopId;

        return this.jdbcTemplate.query(selectShopMenusQuery,
                (rs,rowNum) -> new GetShopMenusRes(
                        rs.getInt("menuId"),
                        rs.getString("menuGroup"),
                        rs.getString("name"),
                        rs.getInt("Price"),
                        rs.getString("menuImgUrl"), // 이것도 리스트로 불러내야 함
                        rs.getString("isRepresent")),
                selectShopMenusParams);
    }

    public List<GetOrderListRes> getOrderList(int userId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월 dd일");
        String getOrderListQuery = "SELECT OrderTable.orderId as orderId, OrderTable.createdAt as orderDate, OrderTable.orderStatus,\n" +
                "       shopName, logoImgUrl, totalAmount, name as menuName, count(name) as menuCount,\n" +
                "       if(( Shop.openTime<( select TIME(NOW()) ) ) and ( ( select TIME(NOW()) )<closeTime ), '영업중', '준비중') as 'openStatus'\n" +
                "FROM OrderTable\n" +
                "         left join Shop on OrderTable.shopId=Shop.shopId\n" +
                "         left join CartInfo on OrderTable.orderId=CartInfo.orderId\n" +
                "         left join Menu on CartInfo.menuId=Menu.menuId\n" +
                "WHERE userId = ?\n" +
                "GROUP BY OrderTable.orderId;";
        int getOrderListParams = userId;
        // 주문 id, 주문 날짜, 주문 상태, 가게 이름, 가게 로고, 주문메뉴 개수, 총 결제금액, 메뉴 이름, 가게 오픈 여부
        return this.jdbcTemplate.query(getOrderListQuery,
                (rs,rownum) -> new GetOrderListRes(
                        rs.getInt("orderId"),
                        rs.getTimestamp("orderDate"),
                        rs.getString("orderStatus"),
                        rs.getString("shopName"),
                        rs.getString("logoImgUrl"),
                        rs.getInt("menuCount"),
                        rs.getInt("totalAmount"),
                        rs.getString("menuName"),
                        rs.getString("openStatus")
                ), getOrderListParams);
    }

    // 존재하는 유저인지?
    public int checkUserExists(int userId){
        String checkUserExistsQuery = "select exists(select userId from User where userId = ?);";
        int checkOrderIdParams = userId;
        return this.jdbcTemplate.queryForObject(checkUserExistsQuery,
                int.class,
                checkOrderIdParams);
    }
    // 존재하는 주문건인지?
    public int checkOrderExists(int orderId){
        String checkOrderExistsQuery = "select exists(select orderId from OrderTable where orderId = ?);";
        int checkOrderIdParams = orderId;
        return this.jdbcTemplate.queryForObject(checkOrderExistsQuery,
                int.class,
                checkOrderIdParams);
    }


    // 주문id가 들어오면 해당 주문건을 주문한 유저가 맞는지?
    public int checkUserMatch(int orderId){
        String checkUserMatchQuery = "select userId from OrderTable where orderId = ?);";
        int checkUserIdParams = orderId;
        return this.jdbcTemplate.queryForObject(checkUserMatchQuery,
                int.class,
                checkUserIdParams);
    }


    // 주문정보 order Info 불러오기 메서드
    public GetOrderInfoRes getOrderInfo(int orderId){
        String getOrderInfoQuery = "select orderId, orderStatus, shopName, OrderTable.createdAt, orderNumber, OrderTable.price, OrderTable.deliveryTip, OrderTable.price+OrderTable.deliveryTip as totalAmount, payment\n" +
                "from OrderTable\n" +
                "    left join Shop on OrderTable.shopId=Shop.shopId\n" +
                "where orderId=?;";
        int getOrderIdParams = orderId;
        //order Information : 주문상태, 가게 이름, 주문일시, 주문번호, 주문금액, 배달팁, 총 결제금액, 결제 방법
        return this.jdbcTemplate.queryForObject(getOrderInfoQuery,
                (rs,rowNum) -> new GetOrderInfoRes(
                        rs.getString("orderStatus"),
                        rs.getString("shopName"),
                        rs.getDate("createdAt"),
                        rs.getString("orderNumber"),
                        rs.getInt("price"),
                        rs.getInt("deliveryTip"),
                        rs.getInt("totalAmount"),
                        rs.getString("payment")),
                getOrderIdParams);
    }

    // 주문정보 order Menu 불러오기 메서드
    public List<GetOrderMenuRes> getOrderMenu(int orderId){
        String getOrderMenuQuery = "select Menu.menuId, Menu.name as menuName, count as menuCount, Menu.price as menuPrice, MenuOption.optionId,\n" +
                "       MenuOption.name as optionName, MenuOption.price as optionPrice\n" +
                "from CartInfo\n" +
                "    left join Menu on CartInfo.menuId=Menu.menuId\n" +
                "    left join MenuOption on CartInfo.optionId = MenuOption.optionId\n" +
                "where CartInfo.orderId=?;";
        int getOrderIdParams = orderId;
        // order Menu :  메뉴id, 주문 메뉴 이름, 메뉴 개수, 주문 메뉴 가격, 옵션id, 주문 옵션이름, 옵션 가격
        return this.jdbcTemplate.query(getOrderMenuQuery,
                (rs,rowNum) -> new GetOrderMenuRes(
                        rs.getInt("menuId"),
                        rs.getString("menuName"),
                        rs.getInt("menuCount"),
                        rs.getInt("menuPrice"),
                        rs.getInt("optionId"),
                        rs.getString("optionName"),
                        rs.getInt("optionPrice")
                        ),getOrderIdParams);
    }

    // 주문정보 order Delivery 불러오기 메서드
    public GetOrderDeliveryRes getOrderDelivery(int orderId){
        String getOrderDeliveryQuery = "select address, User.userTelNum, requestToOwner, requestToRider\n" +
                "from OrderTable left join User on OrderTable.userId=User.userId\n" +
                "where orderid=?;";
        int getOrderIdParams = orderId;

        return this.jdbcTemplate.queryForObject(getOrderDeliveryQuery,
                (rs,rowNum) -> new GetOrderDeliveryRes(
                        rs.getString("address"),
                        rs.getString("userTelNum"),
                        rs.getString("requestToOwner"),
                        rs.getString("requestToRider")
                        ), getOrderIdParams);
    }

    // 배달현황 불러오기 메서드
    public GetDeliveryStatusRes getDeliveryStatus(int orderId){
        String getOrderInfoQuery = "select orderId, orderStatus,\n" +
                "       TIME(TIME(OrderTable.createdAt)+SEC_TO_TIME(deliveryTime*60)) as arriveTime,\n" +
                "       ROUND((TIME(TIME(OrderTable.createdAt)+SEC_TO_TIME(deliveryTime*60))-TIME(NOW()) )/60,0) as leftTime,\n" +
                "       address, shopAddress\n" +
                "from OrderTable left join Shop on OrderTable.shopId=Shop.shopId\n" +
                "where orderId=?;";
        int getOrderIdParams = orderId;
        //# 주문 id, 주문 상태, 도착예상시각, 남은 시간, 배달주소, 가게 주소
        return this.jdbcTemplate.queryForObject(getOrderInfoQuery,
                (rs,rowNum) -> new GetDeliveryStatusRes(
                        rs.getInt("orderId"),
                        rs.getString("orderStatus"),
                        rs.getInt("arriveTime"),
                        rs.getInt("leftTime"),
                        rs.getString("address"),
                        rs.getString("shopAddress")
                ), getOrderIdParams);
    }

}
