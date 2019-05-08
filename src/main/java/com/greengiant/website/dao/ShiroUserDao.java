package com.greengiant.website.dao;

import com.greengiant.website.model.ShiroUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShiroUserDao {
    String TABLE_NAME = "shiro_users";
    String ALL_FIELDS = "id, username, password, password_salt";

    @Select("select " + ALL_FIELDS + " from " + TABLE_NAME)
    List<ShiroUser> getShiroUserList();

    @Select("INSERT INTO shiro_users(`username`,`password`,`password_salt`) VALUES(?, ?, ?)")//todo
    int addUser(ShiroUser user);

    @Select("select * from shiro_users where username=?")//todo
    ShiroUser findUserByName(String name);

}
