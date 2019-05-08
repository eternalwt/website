package com.greengiant.website.dao;

import com.greengiant.website.model.ShiroUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShiroUserDao {
    String TABLE_NAME = "shiro_user";
    String ALL_FIELDS = "id, username, password, password_salt";

    @Select("select " + ALL_FIELDS + " from " + TABLE_NAME)
    List<ShiroUser> getShiroUserList();

    @Select("INSERT INTO " + TABLE_NAME + " set username= #{username}, password= #{password}, password_salt= #{passwordSalt}")
    int addUser(ShiroUser user);

    @Select("select " + ALL_FIELDS + " from " + TABLE_NAME + " where username= #{name}")
    ShiroUser getUserByName(String name);

}
