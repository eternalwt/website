package com.greengiant.website.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.greengiant.website.pojo.model.Article;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    @Delete({
        "delete from article",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

//    @Insert({
//        "insert into article (id, title, ",
//        "column_id, published, ",
//        "audited, read_count, ",
//        "creator_id, create_time, ",
//        "updator_id, update_time, ",
//        "content)",
//        "values (#{id,jdbcType=BIGINT}, #{title,jdbcType=VARCHAR}, ",
//        "#{columnId,jdbcType=BIGINT}, #{published,jdbcType=BIT}, ",
//        "#{audited,jdbcType=BIT}, #{readCount,jdbcType=INTEGER}, ",
//        "#{creatorId,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
//        "#{updatorId,jdbcType=VARCHAR}, #{updateTime,jdbcType=TIMESTAMP}, ",
//        "#{content,jdbcType=LONGVARCHAR})"
//    })
//    int insert(Article record);

    @Select({
        "select",
        "id, title, column_id, published, audited, read_count, creator_id, create_time, ",
        "updator_id, update_time, content",
        "from article",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="title", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="column_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="published", javaType=Boolean.class, jdbcType=JdbcType.BIT),
        @Arg(column="audited", javaType=Boolean.class, jdbcType=JdbcType.BIT),
        @Arg(column="read_count", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="creator_id", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="updator_id", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="content", javaType=String.class, jdbcType=JdbcType.LONGVARCHAR)
    })
    Article selectByPrimaryKey(Long id);

    @Select({
        "select",
        "id, title, column_id, published, audited, read_count, creator_id, create_time, ",
        "updator_id, update_time, content",
        "from article"
    })
    @ConstructorArgs({
        @Arg(column="id", javaType=Long.class, jdbcType=JdbcType.BIGINT, id=true),
        @Arg(column="title", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="column_id", javaType=Long.class, jdbcType=JdbcType.BIGINT),
        @Arg(column="published", javaType=Boolean.class, jdbcType=JdbcType.BIT),
        @Arg(column="audited", javaType=Boolean.class, jdbcType=JdbcType.BIT),
        @Arg(column="read_count", javaType=Integer.class, jdbcType=JdbcType.INTEGER),
        @Arg(column="creator_id", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="create_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="updator_id", javaType=String.class, jdbcType=JdbcType.VARCHAR),
        @Arg(column="update_time", javaType=Date.class, jdbcType=JdbcType.TIMESTAMP),
        @Arg(column="content", javaType=String.class, jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Article> selectAll();

    @Update({
        "update article",
        "set title = #{title,jdbcType=VARCHAR},",
          "column_id = #{columnId,jdbcType=BIGINT},",
          "published = #{published,jdbcType=BIT},",
          "audited = #{audited,jdbcType=BIT},",
          "read_count = #{readCount,jdbcType=INTEGER},",
          "creator_id = #{creatorId,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "updator_id = #{updatorId,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "content = #{content,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Article record);
}