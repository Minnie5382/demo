package com.example.demo.src.review.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserReviewsRes {
    // 유저 id, 총 리뷰 개수(유저), 가게 id, 가게 이름, 리뷰 id, 리뷰 작성일, 리뷰 사진, 리뷰 내용,
    // 별점, 사장님 댓글 내용, 사장님 댓글 작성일, List<GetReviewsMenuRes>
    private int userId;
    private int orderId;
    private int reviewCount;
    private int shopId;
    private String shopName;
    private int reviewId;
    private String reviewDate;
    private String reviewImgUrl;
    private String reviewContent;
    private int star;
    private String ownerComContent;
    private String ownerComDate;
    private List<GetReviewMenusRes> menus;

}
