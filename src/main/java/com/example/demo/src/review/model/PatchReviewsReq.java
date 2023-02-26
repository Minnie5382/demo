package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchReviewsReq {
    private int userId;
    public PatchReviewsReq() {} // json 형식으로 request body를 받아오기 위함

}
