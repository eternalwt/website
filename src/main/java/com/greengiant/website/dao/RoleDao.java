package com.greengiant.website.dao;

import com.greengiant.website.pojo.model.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface RoleDao {
    @Delete({
        "delete from auth_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert({
        "insert into auth_role (id, role_name, ",
        "description)",
        "values (#{id,jdbcType=BIGINT}, #{roleName,jdbcType=VARCHAR}, ",
        "#{description,jdbcType=VARCHAR})"
    })
    int insert(Role record);

    @Select({
        "select",
        "id, role_name, description, create_time, update_time",
        "from auth_role",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="role_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="description", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    Role selectByPrimaryKey(Long id);

    @Select({
            "select",
            "id, role_name, description, create_time, update_time",
            "from auth_role",
            "where role_name = #{name}"
    })
    @ConstructorArgs({
            @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
            @Arg(column="role_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="description", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
            @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    Role selectByName(String name);

    @Select({
        "select",
        "id, role_name, description, create_time, update_time",
        "from auth_role"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="role_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="description", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<Role> selectAll();

    @Update({
        "update auth_role",
        "set role_name = #{roleName,jdbcType=VARCHAR},",
          "description = #{description,jdbcType=VARCHAR} ",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Role record);
}