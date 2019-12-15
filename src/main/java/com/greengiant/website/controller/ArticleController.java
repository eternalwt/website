package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Article;
import com.greengiant.website.service.ArticleService;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResultBean addArticle(@RequestBody Article article) {
        return ResultUtils.success(articleService.save(article));
    }

}
