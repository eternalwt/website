package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("file_info")
public class FileInfo {

    // todo md5字段判断完整性， mime_type字段 suffix字段方便文件分类操作
    // todo 文件和业务数据怎么建立关联？

    @TableId(type= IdType.AUTO)
    private Long id;

    private Long configId;

    private String originalName;

    // todo 搞清楚标准化名字的作用
    private String fileName;

    private String description;

    /**
     * todo 不同类别有不同的存放路径
     */
    private String relative_path;
    /**
     * 文件大小，单位KB
     */
    private Long size;

    private String creatorId;

    private Date createTime;

    private String updatorId;

    private Date updateTime;

    public FileInfo() {
    }

    public FileInfo(Long id, Long configId, String originalName, String fileName, String description, String relative_path, Long size, String creatorId, Date createTime, String updatorId, Date updateTime) {
        this.id = id;
        this.configId = configId;
        this.originalName = originalName;
        this.fileName = fileName;
        this.description = description;
        this.relative_path = relative_path;
        this.size = size;
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

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelative_path() {
        return relative_path;
    }

    public void setRelative_path(String relative_path) {
        this.relative_path = relative_path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
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
        return "FileInfo{" +
                "id=" + id +
                ", configId=" + configId +
                ", originalName='" + originalName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                ", relative_path='" + relative_path + '\'' +
                ", size=" + size +
                ", creatorId='" + creatorId + '\'' +
                ", createTime=" + createTime +
                ", updatorId='" + updatorId + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }

}
