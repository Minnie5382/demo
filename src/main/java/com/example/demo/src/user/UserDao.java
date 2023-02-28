package com.example.demo.src.user;
import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getPwdByEmail(PostLoginReq postLoginReq) {
        String getPwdQuery = "select userId, pwd from User where email = ?;";
        String getEmailParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                    rs.getInt("userId"),
                    rs.getString("pwd")), getEmailParams);

    }

    public User getPwdByEmail(PatchUserReq patchUserReq) {
        String getPwdQuery = "select userId, pwd from User where email = ?;";
        String getEmailParams = patchUserReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userId"),
                        rs.getString("pwd")), getEmailParams);

    }

    public int insertUser(PostUserReq postUserReq, String encryptPwd) {
        String insertUserQuery = "insert into User (nickname, email, pwd,userTelNum, location, emailReceive, smsReceive) values (?,?,?,?,?,?,?);";
        String pwdParams = encryptPwd;

        Object [] insertUserParams = new Object[] {
                postUserReq.getNickname(),
                postUserReq.getEmail(),
                pwdParams,
                postUserReq.getUserTelNum(),
                postUserReq.getLocation(),
                postUserReq.getEmailReceive(),
                postUserReq.getSmsReceive()
        };

        this.jdbcTemplate.update(insertUserQuery, insertUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public int patchUserStatus(int userId) {
        String insertUserQuery = "update User set status = 'DELETED' where userId = ?;";
        int getUserIdParams = userId;

        return this.jdbcTemplate.update(insertUserQuery, getUserIdParams); // 1 : success
    }

    public PatchUserRes updateUser(int userId, PatchUserReq patchUserReq) {
        String updateUserQuery = "update User " +
                "set nickname=?, userTelNum=?, location=?,emailReceive=?,smsReceive=?,profileImgUrl=?" +
                "where userId = ? and status = 'ACTIVE';";
        Object[] updateUserParams = new Object[]{
                patchUserReq.getNickname(),
                patchUserReq.getUserTelNum(),
                patchUserReq.getLocation(),
                patchUserReq.getEmailReceive(),
                patchUserReq.getSmsReceive(),
                patchUserReq.getProfileImgUrl(),
                userId
        };

        this.jdbcTemplate.update(updateUserQuery, updateUserParams); // 1 : success

        PatchUserRes patchUserRes = this.jdbcTemplate.queryForObject("select userId from User where userId=?",
                (rs, rownum) -> new PatchUserRes(rs.getInt("userId")), userId);

        return patchUserRes;

    }


    // 존재하는 이메일인지?
    public int checkEmailExists(String email){
        String checkEmailExistsQuery = "select EXISTS(select userId from User where email=? and status = 'ACTIVE');";
        String checkEmailParams = email;

        return this.jdbcTemplate.queryForObject(checkEmailExistsQuery, int.class, checkEmailParams); // 0 or 1
    }


    // 존재하는 휴대폰 번호인지?
    public int checkTelNumExists(String telNum) {
        String checkTelNumExistsQuery = "select EXISTS(select userId from User where userTelNum=? and status = 'ACTIVE');";
        String checkTelNumParams = telNum;

        return this.jdbcTemplate.queryForObject(checkTelNumExistsQuery, int.class, checkTelNumParams);

    }

    // 존재하는 닉네임인지?
    public int checkNicknameExists(String nickname) {
        String checkNicknameExistsQuery = "select EXISTS(select userId from User where nickname=? and status = 'ACTIVE');";
        String checkNicknameParams = nickname;

        return this.jdbcTemplate.queryForObject(checkNicknameExistsQuery, int.class, checkNicknameParams);
        // queryForObject는 쿼리문의 실행결과를 반환한다. 즉 위 함수의 반환값은 int 형의 0 혹은 1이다.
    }



}
