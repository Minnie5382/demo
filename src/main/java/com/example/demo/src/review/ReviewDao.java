package com.example.demo.src.review;

import com.example.demo.src.review.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetShopReviewsRes> getShopReviews(int shopId) {
        String getReviewsQuery = "select Review.orderId, Shop.shopId as shopId, shopName,\n" +
                "       (select count(*) from Review left join Shop on Review.shopId=Shop.shopId where Review.shopId=?) as shopReviewCount,\n" +
                "       (select count(*) from OwnerComment join Shop on OwnerComment.ownerId=Shop.ownerId where Shop.shopId=?) as ownerComCount,\n" +
                "       Review.reviewId as reviewId, User.userId as userId, User.profileImgUrl as userProfileImgUrl, User.nickname, star, reviewImgUrl,\n" +
                "       Review.content as reviewContent, OC.content as ownerComContent, Review.createdAt as reviewDate, OC.createdAt as ownerComDate\n" +
                "\n" +
                "from Review\n" +
                "         left join Shop on Review.shopId=Shop.shopId\n" +
                "         left join User on Review.userId=User.userId\n" +
                "         left join ReviewImage on Review.reviewId=ReviewImage.reviewId\n" +
                "         left join OwnerComment OC on Review.reviewId = OC.reviewId\n" +
                "         left join CartInfo on Review.orderId=CartInfo.orderId\n" +
                "         left join Menu on CartInfo.menuId=Menu.menuId\n" +
                "where Review.shopId=?\n" +
                "group by userId;";
        int getShopIdParams = shopId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        // 주문id, 가게 리뷰 개수, 사장님 댓글수, 유저id, 유저 프로필 사진 url, 닉네임, 가게 id, 가게 이름, 리뷰 id,
        // 리뷰 작성일, 리뷰 사진, 리뷰 내용, 사장님 댓글 내용, 사장님 댓글 작성일, 별점, List<GetReviewMenusRes>
        return this.jdbcTemplate.query(getReviewsQuery,
                (rs,rowNum) -> new GetShopReviewsRes(
                        rs.getInt("orderId"),
                        rs.getInt("shopReviewCount"),
                        rs.getInt("ownerComCount"),
                        rs.getInt("userId"),
                        rs.getString("userProfileImgUrl"),
                        rs.getString("nickname"),
                        rs.getInt("shopId"),
                        rs.getString("shopName"),
                        rs.getInt("reviewId"),
                        sdf.format(rs.getTimestamp("reviewDate")),
                        rs.getString("reviewImgUrl"),
                        rs.getString("reviewContent"),
                        rs.getString("ownerComContent"),
                        sdf.format(rs.getTimestamp("ownerComDate")),
                        rs.getInt("star"),
                        this.jdbcTemplate.query("select Review.orderId, Menu.name as menu\n" +
                                        "                from Review\n" +
                                        "                         left join User on Review.userId=User.userId\n" +
                                        "                         left join CartInfo on Review.orderId=CartInfo.orderId\n" +
                                        "                         left join Menu on CartInfo.menuId=Menu.menuId\n" +
                                        "                where Review.shopId=? and Review.orderId=?\n" +
                                        "                group by orderId, menu;",
                                (rk, rownum) -> new GetReviewMenusRes(
                                        rk.getInt("orderId"),
                                        rk.getString("menu") ),
                                rs.getInt("shopId"), rs.getInt("orderId")
                        )
                ), getShopIdParams,getShopIdParams,getShopIdParams);
    }

    // 존재하는 가게인지?
    public int checkShopExists(int shopId){
        String checkShopQuery = "select exists(select shopId from Shop where shopId = ?);";
        int checkShopIdParams = shopId;
        return this.jdbcTemplate.queryForObject(checkShopQuery,
                int.class,
                checkShopIdParams);
    }


    // 존재하는 유저인지?
    public int checkUserExists(int userId){
        String checkUserExistsQuery = "select exists(select userId from User where userId = ?);";
        int checkOrderIdParams = userId;
        return this.jdbcTemplate.queryForObject(checkUserExistsQuery,
                int.class,
                checkOrderIdParams);
    }

    public List<GetUserReviewsRes> getUserReviews (int userId){
        String getUserReviewsQuery = "select User.userId as userId, Review.orderId, Shop.shopId as shopId, shopName,\n" +
                "(select count(*) from Review left join User on Review.userId=User.userId where Review.userId=?) as userReviewCount,\n" +
                "Review.createdAt as reviewDate, Review.reviewId as reviewId, star, reviewImgUrl,\n" +
                "Review.content as reviewContent, OC.content as ownerComContent, OC.createdAt as ownerComDate\n" +
                "from Review\n" +
                "left join Shop on Review.shopId=Shop.shopId\n" +
                "         left join User on Review.userId=User.userId\n" +
                "         left join ReviewImage on Review.reviewId=ReviewImage.reviewId\n" +
                "         left join OwnerComment OC on Review.reviewId = OC.reviewId\n" +
                "where Review.userId=?\n" +
                "group by reviewId;";
        int getUserIdParams = userId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        return this.jdbcTemplate.query(getUserReviewsQuery,
                (rs,rowNum) -> new GetUserReviewsRes(
                        // 유저 id, 총 리뷰 개수(유저), 가게 id, 가게 이름, 리뷰 id, 리뷰 작성일, 리뷰 사진, 리뷰 내용,
                        // 별점, 사장님 댓글 내용, 사장님 댓글 작성일, List<GetReviewsMenuRes>
                        rs.getInt("userId"),
                        rs.getInt("orderId"),
                        rs.getInt("userReviewCount"),
                        rs.getInt("shopId"),
                        rs.getString("shopName"),
                        rs.getInt("reviewId"),
                        sdf.format(rs.getTimestamp("reviewDate")),
                        rs.getString("reviewImgUrl"),
                        rs.getString("reviewContent"),
                        rs.getInt("star"),
                        rs.getString("ownerComContent"),
                        sdf.format(rs.getTimestamp("ownerComDate")),
                        this.jdbcTemplate.query("select Review.orderId, Menu.name as menu\n" +
                                        "                from Review\n" +
                                        "                         left join User on Review.userId=User.userId\n" +
                                        "                         left join CartInfo on Review.orderId=CartInfo.orderId\n" +
                                        "                         left join Menu on CartInfo.menuId=Menu.menuId\n" +
                                        "                where Review.shopId=? and Review.orderId=?\n" +
                                        "                group by orderId, menu;",
                                (rk, rownum) -> new GetReviewMenusRes(
                                        rk.getInt("orderId"),
                                        rk.getString("menu")
                                ), rs.getInt("shopId"), rs.getInt("orderId"))
                ), getUserIdParams, getUserIdParams);
    }

    // 리뷰 쓰기 메서드

    public int insertReviews(int userId, PostReviewsReq postReviewsReq){
//        int shopIdByOrderId = this.jdbcTemplate.queryForObject("select shopId from OrderTable where orderId=?;",
//                int.class, postReviewsReq.getOrderId());
        String insertReviewsQuery = "insert into Review (shopId, userId, orderId, star, content) values (?,?,?,?,?);";
        Object [] insertReviewsParams = new Object[] {postReviewsReq.getShopId(), userId, postReviewsReq.getOrderId(),
                postReviewsReq.getStar(), postReviewsReq.getContent()};

        this.jdbcTemplate.update(insertReviewsQuery, insertReviewsParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // 리뷰 이미지 첨부 메서드
    public int insertReviewImgs(int reviewId, PostReviewImgReq postReviewsImgReq) {
        String insertReviewImgsQuery = "insert into ReviewImage(reviewId, reviewImgUrl) values(?,?);";
        Object[] insertReviewImgsParams = new Object[]{reviewId, postReviewsImgReq.getReviewImgUrl()};
        this.jdbcTemplate.update(insertReviewImgsQuery, insertReviewImgsParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // 리뷰 삭제 메서드
    public int deleteReview(int reviewId) {
        return this.jdbcTemplate.update("update Review set status='DELETE' where reviewId=?;", reviewId);
    }


    // 존재하는 리뷰인지?
    public int checkReviewExists(int reviewId){
        String checkReviewExistsQuery =
                "select exists(select reviewId from Review where reviewId = ? and Review.status = 'ACTIVE');";
        int checkReviewIdParams = reviewId;
        return this.jdbcTemplate.queryForObject(checkReviewExistsQuery,
                int.class,
                checkReviewIdParams);
    }



}
