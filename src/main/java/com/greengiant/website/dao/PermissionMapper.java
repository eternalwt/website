package com.greengiant.website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greengiant.website.pojo.model.Perm;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Perm> {
    @Delete({
        "delete from auth_permission",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @Insert({
//        "insert into auth_permission (id, permission_name, ",
//        "description)",
//        "values (#{id,jdbcType=BIGINT}, #{permissionName,jdbcType=VARCHAR}, ",
//        "#{description,jdbcType=VARCHAR})"
//    })
//    int insert(Permission record);

    @Select({
        "select",
        "id, permission_name, description, create_time, update_time",
        "from auth_permission",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="permission_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="description", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    Perm selectByPrimaryKey(Long id);

    @Select({
        "select",
        "id, permission_name, description, create_time, update_time",
        "from auth_permission"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="permission_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="description", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<Perm> selectAll();

    @Update({
        "update auth_permission",
        "set permission_name = #{permissionName,jdbcType=VARCHAR},",
        "description = #{description,jdbcType=VARCHAR} ",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Perm record);
}