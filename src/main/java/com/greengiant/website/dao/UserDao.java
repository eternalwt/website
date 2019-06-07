package com.greengiant.website.dao;

import com.greengiant.website.pojo.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserDao {
    @Delete({
        "delete from auth_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert({
        "insert into auth_user (id, user_name, ",
        "password, password_salt)",
        "values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{passwordSalt,jdbcType=VARCHAR})"
    })
    int insert(User record);

    @Select({
        "select",
        "id, user_name, password, password_salt, create_time, update_time",
        "from auth_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="user_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="password", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="password_salt", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    User selectByPrimaryKey(Long id);

    @Select({
            "select",
            "id, user_name, password, password_salt, create_time, update_time",
            "from auth_user",
            "where user_name = #{username}"
    })
    @ConstructorArgs({
            @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
            @Arg(column="user_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="password", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="password_salt", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
            @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    User selectByName(String username);

    @Select({
        "select",
        "id, user_name, password, password_salt, create_time, update_time",
        "from auth_user"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="user_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="password", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="password_salt", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<User> selectAll();

    @Update({
        "update auth_user",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "password_salt = #{passwordSalt,jdbcType=VARCHAR} ",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(User record);
}