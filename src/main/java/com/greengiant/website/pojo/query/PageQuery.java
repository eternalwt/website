package com.greengiant.website.pojo.query;

import com.greengiant.website.pojo.PageParam;

public class PageQuery<T> {

    /**
     * 原则：
     *   1.QueryWrapper只适合单表的情况，多表关联要自己写语句；
     *   2.用entity来处理相等/like查询；
     *   3.前端给后端传值与QueryWrapper的方法相对应，用的时候再补充;
     *   4.QueryWrapper的allEq是精确查询，因此查询对象里面不用Map，还是用entity，泛型；
     *   5.使用baseMapper.selectPage()来做分页不用自己做count，一个方法搞定获取记录和数量【验证】
     */

    private T entity;

    private PageParam pageParam;

    // todo 根据上面的原则3，可以添加更多属性

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    public void setPageParam(PageParam pageParam) {
        this.pageParam = pageParam;
    }

    public PageQuery() {}

    public PageQuery(T entity, PageParam pageParam) {
        this.entity = entity;
        this.pageParam = pageParam;
    }

    @Override
    public String toString() {
        return "PageQuery{" +
                "entity=" + entity +
                ", pageParam=" + pageParam +
                '}';
    }
}
