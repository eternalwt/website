package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("busi_article")
public class Article {
    @TableId(type= IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long columnId;// todo 添加类别或者字典

    private Boolean published;

    private Boolean audited;

    private Integer readCount;

    private String creatorId;

    private Date createTime;

    private String updatorId;

    private Date updateTime;

    public Article() {
        super();
    }

    public Article(Long id, String title, String content, Long columnId, Boolean published, Boolean audited, Integer readCount, String creatorId, Date createTime, String updatorId, Date updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.columnId = columnId;
        this.published = published;
        this.audited = audited;
        this.readCount = readCount;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.updatorId = updatorId;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getAudited() {
        return audited;
    }

    public void setAudited(Boolean audited) {
        this.audited = audited;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId == null ? null : updatorId.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", columnId=" + columnId +
                ", published=" + published +
                ", audited=" + audited +
                ", readCount=" + readCount +
                ", creatorId='" + creatorId + '\'' +
                ", createTime=" + createTime +
                ", updatorId='" + updatorId + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

}