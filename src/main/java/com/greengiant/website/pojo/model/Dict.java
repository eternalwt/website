package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("sys_dictionary")
public class Dict {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String type;

    private String code;

    private String value;

    private Long parentId;

    private Integer level;

    private Date createTime;

    private Date updateTime;

    public Dict() {
    }

    public Dict(Long id, String type, String code, String value, Long parentId, Integer level, Date createTime, Date updateTime) {
        this.id = id;
        this.type = type;
        this.code = code;
        this.value = value;
        this.parentId = parentId;
        this.level = level;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Dict{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", code='" + code + '\'' +
                ", value='" + value + '\'' +
                ", parentId=" + parentId +
                ", level=" + level +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
