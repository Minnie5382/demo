package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
//
//    public List<GetUserRes> getUsers(){
//        String getUsersQuery = "select * from UserInfo";
//        return this.jdbcTemplate.query(getUsersQuery, // 이 메서드는 리스트를 리턴하고 query라는 메소드를 사용함 -> 복수의 값을 리턴할 때 사용
//                (rs,rowNum) -> new GetUserRes(
//                        rs.getInt("userId"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password"))
//                );
//    }
//
//    public List<GetUserRes> getUsersByEmail(String email){
//        String getUsersByEmailQuery = "select * from UserInfo where email =?";
//        String getUsersByEmailParams = email;
//        return this.jdbcTemplate.query(getUsersByEmailQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userId"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUsersByEmailParams);
//    }
//
//    public GetUserRes getUser(int userId){
//        String getUserQuery = "select * from UserInfo where userId = ?";
//        int getUserParams = userId;
//        return this.jdbcTemplate.queryForObject(getUserQuery, // 리스트 형태로 리턴하지 않는 메소드. 하나의 값을 리턴할 때 사용함.
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userId"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUserParams);
//    }
//
//
    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (nickname, email, userTelNum, profileImageUrl, emailReceive, smsReceive) VALUES (?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getNickname(), postUserReq.getEmail(),
                postUserReq.getUserTelNum(), postUserReq.getProfileImageUrl(), postUserReq.getEmailReceive(),
                postUserReq.getSmsReceive()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()"; // Auto-Index의 마지막 값을 리턴함
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);
    }

    public int checkUserTelNum(String userTelNum){
        String checkUserTelNumQuery = "select exists(select userTelNum from User where userTelNum = ?)";
        String checkUserTelNumParams = userTelNum;
        return this.jdbcTemplate.queryForObject(checkUserTelNumQuery,
                int.class,
                checkUserTelNumParams);
    }

//    public int modifyUserName(PatchUserReq patchUserReq){
//        String modifyUserNameQuery = "update UserInfo set userName = ? where userId = ? ";
//        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUsername(), patchUserReq.getUserId()};
//
//        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
//    }
//
////    public User getPwd(PostLoginReq postLoginReq){
////        String getPwdQuery = "";
////        String getPwdParams = postLoginReq.getId();
////
////        return this.jdbcTemplate.queryForObject(getPwdQuery,
////                (rs,rowNum)-> new User(
////                        rs.getInt("userId"),
////                        rs.getString("nickname"),
////                        rs.getString("email")
////                ),
////                getPwdParams
////                );
////
////    }
//
//
}
