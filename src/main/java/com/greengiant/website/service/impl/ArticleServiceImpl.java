package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.ArticleMapper;
import com.greengiant.website.pojo.PageParam;
import com.greengiant.website.pojo.model.Article;
import com.greengiant.website.pojo.query.PageQuery;
import com.greengiant.website.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> getPageList(PageQuery pageQuery) {
        // todo 搞成模板方法
        // todo getPageList这种方法，适合写在xml里面吗？在家试一下
        Map<String, Object> conditionMap = pageQuery.getCondition();
        PageParam pageParam = pageQuery.getPageParam();

        // todo 1.orderby 2.自定义语句wheresql 先写通 between写通【先把这2个写通】 3.把Page类的所有属性和方法都看一下
        IPage<Article> pg = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
//        page.setRecords();
//        page.setTotal();
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.allEq(conditionMap);
        // todo 看一下下面这个函数，确保没问题 单元测试
        pg = this.page(pg, wrapper);// 底层是baseMapper.selectPage()
//        IPage<Article> result = this.page(pg, wrapper);// 底层是baseMapper.selectPage()

        return pg;
    }

}
