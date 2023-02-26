package com.example.demo.src.review.model;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetShopReviewsRes {
    // 주문id, 가게 리뷰 개수, 사장님 댓글수, 유저id, 유저 프로필 사진 url, 닉네임, 가게 id, 가게 이름, 리뷰 id,
    // 리뷰 작성일, 리뷰 사진, 리뷰 내용, 사장님 댓글 내용, 사장님 댓글 작성일, 별점, List<GetReviewMenusRes>
    private int orderId;
    private int shopReviewCount;
    private int ownerComCount;
    private int userId;
    private String userProfileImgUrl;
    private String nickname;
    private int shopId;
    private String shopName;
    private int reviewId;
    private String reviewDate;
    private String reviewImgUrl;
    private String reviewContent;
    private String ownerComContent;
    private String ownerComDate;
    private int star;
    private List<GetReviewMenusRes> menus;
}
