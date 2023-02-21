package com.example.demo.src.shop;

import com.example.demo.src.shop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ShopDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // 가게 정보 가져오기 메서드
    public GetShopInfoRes selectShopInfo(int shopId){
        String selectShopInfoQuery =
                "SELECT Shop.shopId, shopHomeImgUrl, shopName,  avg(star) as avgStar, count(Review.reviewId) as reviewCount, count(commentId) as ownerComCount, count(dibId) as dibCount, leastPrice, DelTimeFrom, DelTimeTo, deliveryTip\n" +
                        "FROM Shop\n" +
                        "         left join Review on Shop.shopId=Review.shopId\n" +
                        "         left join OwnerComment on Review.reviewId=OwnerComment.reviewId\n" +
                        "         left join Dib on Shop.shopId=Dib.shopId\n" +
                        "         left join ShopHomeImage on Shop.shopId=ShopHomeImage.shopId\n" +
                        "WHERE Shop.shopId=?;";// 결제 방법 컬럼은 아직 DB 업데이트를 전이라, 추후 업데이트 후 추가 예정
        int selectShopInfoParams = shopId;

        return this.jdbcTemplate.queryForObject(selectShopInfoQuery,
                (rs,rowNum) -> new GetShopInfoRes(
                        rs.getInt("shopId"),
                        rs.getString("shopName"),
                        rs.getString("shopHomeImgUrl"),
                        rs.getFloat("avgStar"),
                        rs.getInt("reviewCount"),
                        rs.getInt("ownerComCount"),
                        rs.getInt("dibCount"),
                        rs.getInt("leastPrice"),
                        rs.getInt("delTimeFrom"),
                        rs.getInt("delTimeTo"),
                        rs.getInt("deliveryTip")),
                selectShopInfoParams);
    }

    // 가게 메뉴판 가져오기 메서드
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
                        rs.getString("menuImgUrl"),
                        rs.getString("isRepresent")),
                selectShopMenusParams);
    }

    // Validation : 가게가 존재하는지?
    public int checkShopExists(int shopId){
        String checkShopQuery = "select exists(select shopId from Shop where shopId = ?);";
        int checkShopIdParams = shopId;
        return this.jdbcTemplate.queryForObject(checkShopQuery,
                int.class,
                checkShopIdParams);
    }

}