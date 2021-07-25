package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

// todo 1.搞清楚注解；2.写完后提交一版代码
public class Department {
    @TableId(type= IdType.AUTO)
    private Long id;

    private String deptName;

    private Long parentId;

    private Integer level;

    private Integer sort;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public Department() {
    }

    public Department(Long id, String deptName, Long parentId, Integer level, Integer sort, String remark, Date createTime, Date updateTime) {
        this.id = id;
        this.deptName = deptName;
        this.parentId = parentId;
        this.level = level;
        this.sort = sort;
        this.remark = remark;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "Department{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                ", parentId=" + parentId +
                ", level=" + level +
                ", sort=" + sort +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
