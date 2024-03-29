package com.greengiant.website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greengiant.website.pojo.model.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    @Delete({
        "delete from busi_menu",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Select({
        "select",
        "id, menu_name, number, url, icon, parent_id, sort, in_use, open_way, create_time, update_time",
        "from busi_menu",
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
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    Menu selectByPrimaryKey(Long id);

    @Select({
        "select",
        "id, menu_name, number, url, icon, parent_id, sort, in_use, open_way, create_time, update_time",
        "from busi_menu",
        "where role like '%${roleStr}' order by sort"
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
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<Menu> selectByRole(String roleStr);

    @Select({
        "select",
        "id, menu_name, number, url, icon, parent_id, sort, in_use, open_way, create_time, update_time",
        "from busi_menu"
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
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP)
    })
    List<Menu> selectAll();

    @Update({
        "update busi_menu",
        "set menu_name = #{menuName,jdbcType=VARCHAR},",
          "number = #{number,jdbcType=VARCHAR},",
          "url = #{url,jdbcType=VARCHAR},",
          "icon = #{icon,jdbcType=VARCHAR},",
          "parent_id = #{parentId,jdbcType=BIGINT},",
          "sort = #{sort,jdbcType=INTEGER},",
          "in_use = #{inUse,jdbcType=TINYINT},",
          "open_way = #{openWay,jdbcType=TINYINT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Menu record);

}