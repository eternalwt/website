package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("auth_role_permission")// 如果没有这个注解BaseMapper里面的方法无法使用
public class RolePermission {
    @TableId(type= IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long permissionId;

    private Date createTime;

    private Date updateTime;

    public RolePermission() {
        super();
    }

    public RolePermission(Long roleId, Long permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public RolePermission(Long id, Long roleId, Long permissionId, Date createTime, Date updateTime) {
        this.id = id;
        this.roleId = roleId;
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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}