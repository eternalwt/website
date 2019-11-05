package com.greengiant.website.dao;

import com.greengiant.website.pojo.model.UserRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserRoleDao {
    @Delete({
        "delete from auth_user_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert({
        "insert into auth_user_role (id, user_id, ",
        "role_id)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{roleId,jdbcType=BIGINT})"
    })
    int insert(UserRole record);

    @Select({
        "select",
        "id, user_id, role_id, create_time, update_time",
        "from auth_user_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="user_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="role_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    UserRole selectByPrimaryKey(Long id);

    @Select({
            "select",
            "id, user_id, role_id, create_time, update_time",
            "from auth_user_role",
            "where user_id = #{userId,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
            @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
            @Arg(column="user_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
            @Arg(column="role_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
            @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
            @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserRole> selectByUserId(Long userId);

    @Select({
        "select",
        "id, user_id, role_id, create_time, update_time",
        "from auth_user_role"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="user_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="role_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserRole> selectAll();

    @Update({
        "update auth_user_role",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "role_id = #{roleId,jdbcType=BIGINT} ",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserRole record);
}