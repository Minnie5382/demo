package com.example.demo.src.user;

import com.example.demo.src.review.model.PostReviewsReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;


    // 회원가입 메서드
    public int insertUser(String userTelNum, PostUserReq postUserReq){
        String insertUserQuery =
                "insert into User (nickname, email, userTelNum, location,emailReceive, smsReceive, profileImgUrl) values (?,?,?,?,?,?,?);";
        Object [] insertUserParams = new Object[] {
                postUserReq.getNickname(),
                postUserReq.getEmail(),
                postUserReq.getUserTelNum(),
                postUserReq.getLocation(),
                postUserReq.getEmailReceive(),
                postUserReq.getSmsReceive(),
                postUserReq.getProfileImgUrl()
        };

        this.jdbcTemplate.update(insertUserQuery,insertUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }


    // 유저 정보 수정 메서드
    public int updateUser(int userId, PatchUserReq patchUserReq){
        String insertUserQuery = "update User \n" +
                "set nickname=?,\n" +
                "    email=?,\n" +
                "    userTelNum=?,\n" +
                "    location=?,\n" +
                "    emailReceive=?,\n" +
                "    smsReceive=?,\n" +
                "    profileImgUrl=?\n" +
                "where userId=?;";
        Object [] insertUserParams = new Object[] {
                patchUserReq.getNickname(),
                patchUserReq.getUserTelNum(),
                patchUserReq.getEmail(),
                patchUserReq.getLocation(),
                patchUserReq.getEmailReceive(),
                patchUserReq.getSmsReceive(),
                patchUserReq.getProfileImgUrl(),
                userId
        };

        return this.jdbcTemplate.update(insertUserQuery,insertUserParams);
    }

    // 회원 탈퇴 메서드
    public int deleteUser(int userId) {
        int result = this.jdbcTemplate.update("update User set status='DELETE' where userId=?;", userId);
        return result;
    }

}
