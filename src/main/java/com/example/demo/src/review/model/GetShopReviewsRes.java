package com.example.demo.src.review.model;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetShopReviewsRes {
    // 가게 id, 가게 이름, 가게 리뷰 개수, 사장님 댓글수, 리뷰 id, 유저id, 유저 프로필 사진 url, 닉네임, 리뷰 별점, 리뷰 사진, 리뷰 내용,
    // 사장님 댓글 내용, 주문 메뉴이름
    // 공통 부분 GetReviewRes : 가게 id, 가게 이름, 리뷰 id, 리뷰 작성일, 리뷰 사진, 리뷰 내용, 메뉴명, 사장님 댓글 내용, 사장님 댓글 작성일, 별점

    private int orderId;
    private int ReviewCount;
    private int ownerComCount;
    private int userId;
    private String userProfileImgUrl;
    private String nickname;
    private List<GetReviewRes> review;
    private List<GetReviewMenusRes> menus;
}
