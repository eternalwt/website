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
     * 资源名称
     */
    private String resource;// todo 这个字段怎么用好，提供接口的时候就知道了

    /**
     * 资源类别，例如1代表菜单，2代表按钮，3代表接口
     */
    private String resourceType;

    /**
     * 资源id，资源实体对应的主键（与entityId类似，代表其他真正存放各类资源详情表的表主键）
     */
    private Long resourceId;

    /**
     * （对资源的）操作，例如增删查改
     */
    private String operation;

    private Date createTime;

    private Date updateTime;

    public Perm() {
        super();
    }

    public Perm(Long id, String entity, Long entityId, String resource, String resourceType, Long resourceId, String operation, Date createTime, Date updateTime) {
        this.id = id;
        this.entity = entity;
        this.entityId = entityId;
        this.resource = resource;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
        this.operation = operation;
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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
                ", resourceType='" + resourceType + '\'' +
                ", resourceId=" + resourceId +
                ", operation='" + operation + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}