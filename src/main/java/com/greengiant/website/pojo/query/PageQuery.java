package com.greengiant.website.pojo.query;

import com.greengiant.website.pojo.PageParam;

import java.util.Map;

public class PageQuery {
    /*
    * 相比使用entity和ParamBean两个后端参数来做分页，封装成一个PageQuery语义更清晰（不用把whereSql封装到ParamBean中）
    * 原则：
    *   1.用entity和vo来处理单表/多表连接的相等条件查询
    *   2.
    * */
    // todo 如果这里用泛型，是不是后端代码比公司的还简洁一些？【比较和我现在用的map的优劣】
    // todo 思考：entity和vo可以cover相等，那么其他条件呢？例如 日期 > < != 时间范围between等？
    // todo 再综合考虑下whereSql。把查询的问题整理总结一下
    // todo 【where里面能否遍历entity的字段？这样就不用写一堆重复代码了】
    // todo 分页搞成自动添加、拼接（所有mapper共用的常量）
    // todo 排序、分页如何进一步提高抽象程度？(让代码写的更简洁)【自动生成这段语句，类似whereSql】
    // todo 同时还要结合mybatis-plus

//    private T entity;

    private Map<String, Object> condition;

    private PageParam pageParam;

    public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public PageParam getPageParam() {
        return pageParam;
    }

    public void setPageParam(PageParam pageParam) {
        this.pageParam = pageParam;
    }

    public PageQuery() {}

    public PageQuery(Map<String, Object> condition, PageParam pageParam) {
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
