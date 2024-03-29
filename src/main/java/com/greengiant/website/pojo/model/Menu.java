package com.greengiant.website.pojo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("busi_menu")
public class Menu {
    @TableId(type= IdType.AUTO)
    private Long id;

    private String menuName;

    private String number;

    private String url;

    private String icon;

    private Long parentId;

    private Integer sort;

    private Byte inUse;

    private Byte openWay;

    private Date createTime;

    private Date updateTime;

    public Menu() {
        super();
    }

    public Menu(Long id, String menuName, String number, String url, String icon, Long parentId, Integer sort, Byte inUse, Byte openWay, Date createTime, Date updateTime) {
        this.id = id;
        this.menuName = menuName;
        this.number = number;
        this.url = url;
        this.icon = icon;
        this.parentId = parentId;
        this.sort = sort;
        this.inUse = inUse;
        this.openWay = openWay;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getInUse() {
        return inUse;
    }

    public void setInUse(Byte inUse) {
        this.inUse = inUse;
    }

    public Byte getOpenWay() {
        return openWay;
    }

    public void setOpenWay(Byte openWay) {
        this.openWay = openWay;
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
        return "Menu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", number='" + number + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", parentId=" + parentId +
                ", sort=" + sort +
                ", inUse=" + inUse +
                ", openWay=" + openWay +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}