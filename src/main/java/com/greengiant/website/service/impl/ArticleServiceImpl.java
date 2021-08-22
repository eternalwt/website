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

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> getPageList(PageQuery pageQuery) {
        // todo 使用mybatis-plus来做分页
        Map conditionMap = (LinkedHashMap)pageQuery.getCondition();
        PageParam pageParam = pageQuery.getPageParam();

        IPage<Article> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.allEq(conditionMap);
        IPage<Article> result = this.page(page, wrapper);

        return result;
    }

}
