package com.greengiant.website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greengiant.website.pojo.model.RolePermission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    @Delete({
        "delete from auth_role_permission",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert({
        "insert into auth_role_permission (id, role_id, ",
        "permission_id)",
        "values (#{id,jdbcType=BIGINT}, #{roleId,jdbcType=BIGINT}, ",
        "#{permissionId,jdbcType=BIGINT})"
    })
    int insert(RolePermission record);

    @Select({
        "select",
        "id, role_id, permission_id, create_time, update_time",
        "from auth_role_permission",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="role_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="permission_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    RolePermission selectByPrimaryKey(Long id);

    @Select({
        "select",
        "id, role_id, permission_id, create_time, update_time",
        "from auth_role_permission"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="role_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="permission_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<RolePermission> selectAll();

    @Update({
        "update auth_role_permission",
        "set role_id = #{roleId,jdbcType=BIGINT},",
          "permission_id = #{permissionId,jdbcType=BIGINT} ",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(RolePermission record);
}