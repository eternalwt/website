package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

public class Article {
    @TableId(type= IdType.AUTO)
    private Long id;

    private String title;

    private Long columnId;

    private Boolean published;

    private Boolean audited;

    private Integer readCount;

    private String creatorId;

    private Date createTime;

    private String updatorId;

    private Date updateTime;

    private String content;

    public Article(Long id, String title, Long columnId, Boolean published, Boolean audited, Integer readCount, String creatorId, Date createTime, String updatorId, Date updateTime, String content) {
        this.id = id;
        this.title = title;
        this.columnId = columnId;
        this.published = published;
        this.audited = audited;
        this.readCount = readCount;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.updatorId = updatorId;
        this.updateTime = updateTime;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", columnId=" + columnId +
                ", published=" + published +
                ", audited=" + audited +
                ", readCount=" + readCount +
                ", creatorId='" + creatorId + '\'' +
                ", createTime=" + createTime +
                ", updatorId='" + updatorId + '\'' +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                '}';
    }

    public Article() {
        super();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

}