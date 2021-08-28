package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("file_config")
public class FileConfig {

    @TableId(type= IdType.AUTO)
    private Long id;

    private String configName;

    private String description;

    private String type;// todo 用来存文件存放的业务目录等

    private String creatorId;

    private Date createTime;

    private String updatorId;

    private Date updateTime;

    public FileConfig() {
    }

    public FileConfig(Long id, String configName, String description, String type, String creatorId, Date createTime, String updatorId, Date updateTime) {
        this.id = id;
        this.configName = configName;
        this.description = description;
        this.type = type;
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

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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
        this.updatorId = updatorId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "FileConfig{" +
                "id=" + id +
                ", configName='" + configName + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", createTime=" + createTime +
                ", updatorId='" + updatorId + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}
