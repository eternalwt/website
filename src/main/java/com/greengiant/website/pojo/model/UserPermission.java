package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("auth_user_permission")
public class UserPermission {
    @TableId(type= IdType.AUTO)
    private Long id;

    private Long userId;

    private Long permissionId;

    private Date createTime;

    private Date updateTime;

    public UserPermission() {
        super();
    }

    public UserPermission(Long id, Long userId, Long permissionId, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.permissionId = permissionId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
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
        return "RolePermission{" +
                "id=" + id +
                ", roleId=" + userId +
                ", permissionId=" + permissionId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
