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
public class PostUserReq {
    // 이메일 수신 동의, 문자 수신 동의,배달주소, 휴대폰 번호, 닉네임, 이메일주소,
    private String nickname;
    private String userTelNum;
    private String email;
    private String location;
    private String emailReceive;
    private String smsReceive;
    public PostUserReq() {};

}
