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
    public IPage<Article> getPageList(PageQuery<Article> pageQuery) {
        Article article = null;
        if (pageQuery.getEntity() != null) {
            article = pageQuery.getEntity();
        }

        PageParam pageParam = new PageParam();
        if (pageQuery.getPageParam() != null) {
            pageParam = pageQuery.getPageParam();
        }

        // todo getPageList这种方法，适合写在xml里面吗？在家试一下
        // todo 把Page类的所有属性和方法都看一下
        // todo 看一下下面this.page函数的实现，单元测试
        IPage<Article> pg = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Article> wrapper = generateQueryWrapper(article);
        pg = this.page(pg, wrapper);// 底层是baseMapper.selectPage()

        return pg;
    }

    private QueryWrapper<Article> generateQueryWrapper(Article article) {// todo 感觉应该传pageQuery把所有的查询条件都封装进来？
        QueryWrapper<Article> wrapper = new QueryWrapper<>();

        if (article == null) {
            return wrapper;
        }

        if (article.getTitle() != null && !"".equals(article.getTitle())) {
            wrapper.like("title", article.getTitle());
        }
        // 根据需要添加更多字段

        return wrapper;
    }

}
