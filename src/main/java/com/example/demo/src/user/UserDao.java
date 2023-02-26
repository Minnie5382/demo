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
    public int insertUser(PostUserReq postUserReq){
        String insertUserQuery =
                "insert User(nickname, email, userTelNum, location,emailReceive, smsReceive) values (?,?,?,?,?,?);";
        Object [] insertUserParams = new Object[] {
                postUserReq.getNickname(),
                postUserReq.getEmail(),
                postUserReq.getUserTelNum(),
                postUserReq.getLocation(),
                postUserReq.getEmailReceive(),
                postUserReq.getSmsReceive()};

        this.jdbcTemplate.update(insertUserQuery, insertUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

}
