package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {
    private String nickname;
    private String email;
    private String userTelNum;
    private String profileImageUrl;
    private String emailReceive;
    private String smsReceive;
}
