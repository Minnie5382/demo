package com.example.demo.src.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserReq {
    private String nickname;
    private String email;
    private String userTelNum;
    private String location;
    private String emailReceive;
    private String smsReceive;
    private String profileImgUrl;
    public PatchUserReq() {} // json 형식으로 request body를 받아오기 위함

}
