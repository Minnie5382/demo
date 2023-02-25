package com.example.demo.src.review.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetReviewRes {
    // 공통 부분 GetReviewRes
    // 가게 id, 가게 이름, 리뷰 id, 리뷰 작성일, 리뷰 사진, 리뷰 내용, 사장님 댓글 내용, 사장님 댓글 작성일, 별점
    private int shopId;
    private String shopName;
    private int reviewId;
    private Timestamp reviewDate;
    private String reviewImgUrl;
    private String reviewContent;
    private String ownerComContent;
    private Date ownerComDate;
    private int star;
}
