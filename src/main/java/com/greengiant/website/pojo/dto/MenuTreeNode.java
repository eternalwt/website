package com.greengiant.website.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeNode {

    private Long id;

    private String  value;

    private String url;

    private List<MenuTreeNode> children;

    public MenuTreeNode(Long id, String value, String url) {
        this.id = id;
        this.value = value;
        this.url = url;
        this.children = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MenuTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<MenuTreeNode> children) {
        this.children = children;
    }
}
