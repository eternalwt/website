package com.greengiant.website.dao.user;

import com.greengiant.website.model.ShiroUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {
    String TABLE_NAME = "shiro_users";
    String ALL_FIELDS = "id, username, password, password_salt";

    @Select("select " + ALL_FIELDS + " from " + TABLE_NAME)
    List<ShiroUser> list();
}
