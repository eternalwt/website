package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("auth_permission")// 如果没有这个注解BaseMapper里面的方法无法使用
public class Permission {
    @TableId(type= IdType.AUTO)
    private Long id;

    private String permissionName;

    private String description;

    private Date createTime;

    private Date updateTime;

    public Permission(Long id, String permissionName, String description, Date createTime, Date updateTime) {
        this.id = id;
        this.permissionName = permissionName;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Permission() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
        return "Permission{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}