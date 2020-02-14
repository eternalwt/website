package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greengiant.website.pojo.PageParam;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Article;
import com.greengiant.website.pojo.vo.PageQuery;
import com.greengiant.website.service.ArticleService;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "add")
    public ResultBean addArticle(@RequestBody Article article) {
        return ResultUtils.success(articleService.save(article));
    }

//    @PostMapping(value = "list")
//    public ResultBean getPageList(@RequestBody PageParam pageParam) {// todo 分页加条件过滤，形成一个通用的操作
//        IPage<Article> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
//        QueryWrapper<Article> wrapper = new QueryWrapper<>();// todo 分页加条件过滤，形成一个通用的操作
//        IPage<Article> result = articleService.page(page, wrapper);
//
//        return ResultUtils.success(result);
//    }

    @PostMapping(value = "list")
    public ResultBean getPageList(@RequestBody PageQuery pageQuery) {
        // todo 判空的问题？
        Map conditionMap = (LinkedHashMap)pageQuery.getCondition();
        PageParam pageParam = pageQuery.getPageParam();

        IPage<Article> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<>();// todo 分页加条件过滤，形成一个通用的操作
        wrapper.allEq(conditionMap);
        IPage<Article> result = articleService.page(page, wrapper);

        return ResultUtils.success(result);
    }

}
