package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Article;
import com.greengiant.website.pojo.query.PageQuery;
import com.greengiant.website.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "/add")
    public ResultBean addArticle(@RequestBody Article article) {
        return ResultUtils.success(articleService.save(article));
    }

    @PostMapping(value = "/list")
    public ResultBean getPageList(@RequestBody PageQuery<Article> pageQuery) {
        IPage<Article> result = articleService.getPageList(pageQuery);
        return ResultUtils.success(result);
    }

}
