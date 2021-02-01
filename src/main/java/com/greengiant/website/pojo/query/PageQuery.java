package com.greengiant.website.pojo.query;

import com.greengiant.website.pojo.PageParam;

public class PageQuery {
    private Object condition;

    private PageParam pageParam;

    public Object getCondition() {
        return condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    public void setPageParam(PageParam pageParam) {
        this.pageParam = pageParam;
    }

    public PageQuery() {}

    public PageQuery(Object condition, PageParam pageParam) {
        this.condition = condition;
        this.pageParam = pageParam;
    }

    @Override
    public String toString() {
        return "PageQuery{" +
                "condition=" + condition +
                ", pageParam=" + pageParam +
                '}';
    }
}
