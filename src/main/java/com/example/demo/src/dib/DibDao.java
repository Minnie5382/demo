package com.example.demo.src.dib;

import com.example.demo.src.dib.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DibDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 찜 누르기 메서드
    public PostDibRes retrieveDib(int userId, int shopId){
        String insertDibQuery = "insert into Dib(userId, shopId) values (?,?)";
        Object[] insertDibParams = new Object[] {userId, shopId};

        this.jdbcTemplate.update(insertDibQuery,insertDibParams);

        String lastInsertIdQuery = "select last_insert_id()";
        int dibId = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

        return this.jdbcTemplate.queryForObject("select userId, shopId, dibId from Dib where dibId=?;",
                (rs,rownum) -> new PostDibRes (
                        rs.getInt("userId"),
                        rs.getInt("shopId"),
                        rs.getInt("dibId")), dibId);
    }

}
