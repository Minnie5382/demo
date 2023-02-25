package com.example.demo.src.review;

import com.example.demo.src.review.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.*;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetReviewRes> getReviewRes(int reviewId) {
        String selectReviewsQuery = "";
        int selectReviewIdParams = reviewId;
        return this.jdbcTemplate.query(selectReviewsQuery,
                // 가게 id, 가게 이름, 리뷰 id, 리뷰 작성일, 리뷰 사진, 리뷰 내용, 사장님 댓글 내용, 사장님 댓글 작성일, 별점
                (rs,rowNum) -> new GetReviewRes(
                        rs.getInt("shopId"),
                        rs.getString("shopName"),
                        rs.getInt("reviewId"),
                        rs.getTimestamp("reviewDate"),
                        rs.getString("reviewImgUrl"),
                        rs.getString("reviewContent"),
                        rs.getString("ownerComContent"),
                        rs.getDate("ownerComDate"),
                        rs.getInt("star")
                        ), selectReviewIdParams);
    }

    public List<GetShopReviewsRes> getShopReviews(int shopId) {
        String selectReviewsQuery = "select Review.orderId, Shop.shopId as shopId, shopName,\n" +
                "       (select count(*) from Review left join Shop on Review.shopId=Shop.shopId where Review.shopId=1) as reviewCount,\n" +
                "       (select count(*) from OwnerComment join Shop on OwnerComment.ownerId=Shop.ownerId where Shop.shopId = 1) as ownerComCount,\n" +
                "       Review.reviewId as reviewId, User.userId as userId, User.profileImgUrl as userProfileImgUrl, User.nickname, star, reviewImgUrl,\n" +
                "       Review.content as reviewContent, OC.content as ownerComContent\n" +
                "from Review\n" +
                "         left join Shop on Review.shopId=Shop.shopId\n" +
                "         left join User on Review.userId=User.userId\n" +
                "         left join ReviewImage on Review.reviewId=ReviewImage.reviewId\n" +
                "         left join OwnerComment OC on Review.reviewId = OC.reviewId\n" +
                "         left join CartInfo on Review.orderId=CartInfo.orderId\n" +
                "         left join Menu on CartInfo.menuId=Menu.menuId\n" +
                "where Review.shopId=?\n" +
                "group by userId;";
        int selectShopIdParams = shopId;
        // 가게 id, 가게 이름, 가게 리뷰 개수, 사장님 댓글수, 리뷰 id, 유저id, 유저 프로필 사진 url, 닉네임, 리뷰 별점, 리뷰 사진, 리뷰 내용,
        // 사장님 댓글 내용, 주문 메뉴 이름
        return this.jdbcTemplate.query(selectReviewsQuery,
                (rs,rowNum) -> new GetShopReviewsRes( // builder
                        rs.getInt("orderId"),
                        rs.getInt("shopId"),
                        rs.getString("shopName"),
                        rs.getInt("reviewCount"),
                        rs.getInt("ownerComCount"),
                        rs.getInt("reviewId"),
                        rs.getInt("userId"),
                        rs.getString("userProfileImgUrl"),
                        rs.getString("nickname"),
                        rs.getInt("star"),
                        rs.getString("reviewImgUrl"),
                        rs.getString("reviewContent"),
                        rs.getString("ownerComContent"),
                        this.jdbcTemplate.query("select Review.orderId, Menu.name as menu\n" +
                                        "             from Review\n" +
                                        "                     left join User on Review.userId=User.userId\n" +
                                        "                     left join CartInfo on Review.orderId=CartInfo.orderId\n" +
                                        "                     left join Menu on CartInfo.menuId=Menu.menuId\n" +
                                        "             where Review.shopId=? and Review.orderId=?\n" +
                                        "             group by orderId, menu;",
                                (rk, rownum) -> new GetReviewMenusRes(
                                        rk.getInt("orderId"),
                                        rk.getString("menu")
                                ), rs.getInt("shopId"), rs.getInt("orderId")
                        )), selectShopIdParams);
    }

    // 존재하는 가게인지?
    public int checkShopExists(int shopId){
        String checkShopQuery = "select exists(select shopId from Shop where shopId = ?);";
        int checkShopIdParams = shopId;
        return this.jdbcTemplate.queryForObject(checkShopQuery,
                int.class,
                checkShopIdParams);
    }



}
