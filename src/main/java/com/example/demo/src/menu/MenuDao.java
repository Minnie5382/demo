package com.example.demo.src.menu;

import com.example.demo.src.menu.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MenuDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // 메뉴 상세보기 메서드
    public List<GetMenusRes> selectMenus(int menuId){
        String selectMenusQuery = "SELECT Menu.menuId, Menu.name as menuName, Menu.price as menuPrice,\n" +
                "       Menu.description as menuDes\n" +
                "FROM Menu\n" +
                "         left join MenuOption on Menu.menuId=MenuOption.menuId\n" +
                "         join Shop on Menu.shopId=Shop.shopId\n" +
                "         left join MenuImage on Menu.menuId=MenuImage.menuId\n" +
                "WHERE Menu.menuId=?\n" +
                "GROUP BY Menu.menuId;";

        int selectMenusParams = menuId;

        return this.jdbcTemplate.query(selectMenusQuery,
                (rs,rowNum) -> {
                    return new GetMenusRes(
                            rs.getInt("menuId"),
                            rs.getString("menuName"),
                            rs.getString("menuDes"),
                            rs.getInt("menuPrice"),
                            this.jdbcTemplate.query("select menuImgId, menuImgUrl\n" +
                                            "from MenuImage\n" +
                                            "where menuId=?;",
                                    (rk, rowNum1) -> new GetMenuImgRes(
                                            rk.getInt("menuImgId"),
                                            rk.getString("menuImgUrl")), rs.getInt("menuId")
                            ),
                            this.jdbcTemplate.query("select optionId, optionGroup, name as optionName, price as optionPrice\n" +
                                            "from MenuOption\n" +
                                            "where menuId=?;",
                                    (rv, rowNum2) -> new GetOptionRes(
                                            rv.getInt("optionId"),
                                            rv.getString("optionGroup"),
                                            rv.getString("optionName"),
                                            rv.getInt("optionPrice")),
                                    rs.getInt("menuId")));
                }
        ,selectMenusParams);
    }

    // Validation : 가게가 존재하는지?
    public int checkShopExists(int shopId){
        String checkShopQuery = "select exists(select shopId from Shop where shopId = ?);";
        int checkShopIdParams = shopId;
        return this.jdbcTemplate.queryForObject(checkShopQuery,
                int.class,
                checkShopIdParams);
    }

    // menuId가 들어오면 해당 가게가 존재하는지?
    public int checkMenuExists(int menuId){
        String checkShopQuery = "select exists(select shopId from Menu where menuId = ?);";
        int checkMenuIdParas = menuId;
        return this.jdbcTemplate.queryForObject(checkShopQuery,
                int.class,
                checkMenuIdParas);
    }

    // menuId가 들어오면 해당 메뉴가 있는지?
    public int checkShopOfMenuExists(int menuId) {
        String checkShopQuery = "select exists(select menuId from Menu where menuId = ?);";
        int checkShopOfMenuParams = menuId;
        return this.jdbcTemplate.queryForObject(checkShopQuery,
                int.class,
                checkShopOfMenuParams);
    }

}
