package com.greengiant.website.dao;

import com.greengiant.website.pojo.model.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface MenuMapper {
    @Delete({
        "delete from Menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into Menu (id, menu_name, ",
        "number, url, icon, ",
        "parent_id, sort, in_use, ",
        "open_way, role, user, ",
        "create_time, update_time)",
        "values (#{id,jdbcType=BIGINT}, #{menuName,jdbcType=VARCHAR}, ",
        "#{number,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{icon,jdbcType=VARCHAR}, ",
        "#{parentId,jdbcType=BIGINT}, #{sort,jdbcType=INTEGER}, #{inUse,jdbcType=TINYINT}, ",
        "#{openWay,jdbcType=TINYINT}, #{role,jdbcType=VARCHAR}, #{user,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(Menu record);

    @Select({
        "select",
        "id, menu_name, number, url, icon, parent_id, sort, in_use, open_way, role, user, ",
        "create_time, update_time",
        "from Menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="menu_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="number", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="url", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="icon", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="parent_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="sort", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="in_use", javaType=Byte.class, jdbcType=JdbcType.TINYINT),
        @Arg(column="open_way", javaType=Byte.class, jdbcType=JdbcType.TINYINT),
        @Arg(column="role", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="user", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    Menu selectByPrimaryKey(Long id);

    @Select({
            "select",
            "id, menu_name, number, url, icon, parent_id, sort, in_use, open_way, role, user, ",
            "create_time, update_time",
            "from Menu",
            "where role like '%${roleStr}'"
    })
    @ConstructorArgs({
            @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
            @Arg(column="menu_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="number", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="url", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="icon", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="parent_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
            @Arg(column="sort", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
            @Arg(column="in_use", javaType=Byte.class, jdbcType=JdbcType.TINYINT),
            @Arg(column="open_way", javaType=Byte.class, jdbcType=JdbcType.TINYINT),
            @Arg(column="role", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="user", javaType=String.class, jdbcType=JdbcType.VARCHAR),
            @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
            @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<Menu> selectByRole(String roleStr);

    @Select({
        "select",
        "id, menu_name, number, url, icon, parent_id, sort, in_use, open_way, role, user, ",
        "create_time, update_time",
        "from Menu"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="menu_name", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="number", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="url", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="icon", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="parent_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="sort", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="in_use", javaType=Byte.class, jdbcType=JdbcType.TINYINT),
        @Arg(column="open_way", javaType=Byte.class, jdbcType=JdbcType.TINYINT),
        @Arg(column="role", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="user", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<Menu> selectAll();

    @Update({
        "update Menu",
        "set menu_name = #{menuName,jdbcType=VARCHAR},",
          "number = #{number,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "icon = #{icon,jdbcType=VARCHAR},",
          "parent_id = #{parentId,jdbcType=BIGINT},",
          "sort = #{sort,jdbcType=INTEGER},",
          "in_use = #{inUse,jdbcType=TINYINT},",
          "open_way = #{openWay,jdbcType=TINYINT},",
          "role = #{role,jdbcType=VARCHAR},",
          "user = #{user,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Menu record);
}