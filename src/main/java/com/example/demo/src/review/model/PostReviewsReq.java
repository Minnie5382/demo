package com.example.demo.src.review.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewsReq {
    private int userId;
    private int shopId;
    private int orderId;
    private String content;
    private int star;
    private List<PostReviewImgReq> imgs;
    public PostReviewsReq() {} // json 형식으로 request body를 받아오기 위함

}
