package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("auth_permission")// 如果没有这个注解BaseMapper里面的方法无法使用
public class Perm {
    @TableId(type= IdType.AUTO)
    private Long id;

    /**
     * 权限的实体，可以是角色、角色实例、用户、机构等等（支持基于角色、基于用户等多种方式鉴权）
     */
    private String entity;

    /**
     * 权限实体对应的主键（其他真正存放各类实体详情表的表主键）
     */
    private Long entityId;

    /**
     * 资源[名称]（对应wildcarPermission里面的资源）
     */
    private String resource;

    /**
     * 资源代码，例如1代表菜单，2代表按钮，3代表接口
     */
    private String resourceCode;

    /**
     * 操作，例如增删查改、打印、下载（对应wildcarPermission里面的操作）
     */
    private String operation;

    /**
     * 实例（对应wildcarPermission里面的实例）
     */
    private String resourceInstance;

    /**
     * 资源id，资源实体对应的主键（与entityId类似，代表其他真正存放各类资源详情表的表主键）
     */
    private Long resourceId;

    private Date createTime;

    private Date updateTime;

    public Perm() {
        super();
    }

    public Perm(Long id, String entity, Long entityId, String resource, String resourceCode, String operation, String resourceInstance, Long resourceId, Date createTime, Date updateTime) {
        this.id = id;
        this.entity = entity;
        this.entityId = entityId;
        this.resource = resource;
        this.resourceCode = resourceCode;
        this.operation = operation;
        this.resourceInstance = resourceInstance;
        this.resourceId = resourceId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResourceInstance() {
        return resourceInstance;
    }

    public void setResourceInstance(String resourceInstance) {
        this.resourceInstance = resourceInstance;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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
        return "Perm{" +
                "id=" + id +
                ", entity='" + entity + '\'' +
                ", entityId=" + entityId +
                ", resource='" + resource + '\'' +
                ", resourceCode='" + resourceCode + '\'' +
                ", operation='" + operation + '\'' +
                ", resourceInstance='" + resourceInstance + '\'' +
                ", resourceId=" + resourceId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}