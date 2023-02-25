package com.example.demo.src.review.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMyReviewsRes {
    // 유저 리뷰목록 : 총 리뷰 개수(유저), 가게 id, 가게 이름, 리뷰 id, 리뷰 작성일, 리뷰 사진, 리뷰 내용, 메뉴명,
    // 별점, 사장님 댓글 내용, 사장님 댓글 작성일

    // 공통 부분 GetReviewRes : 가게 id, 가게 이름, 리뷰 id, 리뷰 작성일, 리뷰 사진, 리뷰 내용, 메뉴명, 사장님 댓글 내용, 사장님 댓글 작성일, 별점
    // -> 하나의 모델로 묶자
    private int userId;
    private int reviewCount;
    private List<GetReviewRes> review;
    private List<GetReviewMenusRes> menus;

}
