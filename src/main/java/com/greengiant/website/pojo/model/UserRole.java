package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("auth_user_role")
public class UserRole {
    @TableId(type= IdType.AUTO)
    private Long id;

    private Long userId;

    private Long roleId;

    private Date createTime;

    private Date updateTime;

    public UserRole(Long id, Long userId, Long roleId, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public UserRole() {
        super();
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
}