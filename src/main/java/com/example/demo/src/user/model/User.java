package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userId;
    private String nickname;
    private String grade;
    private String email;
    private String userTelNum;
    private String profileImageUrl;
    private int pointBalance;
    private String location;
    private char emailReceive;
    private char smsReceive;
}
