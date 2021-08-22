package com.greengiant.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.Article;
import com.greengiant.website.pojo.query.PageQuery;

public interface ArticleService extends IService<Article> {

    IPage<Article> getPageList(PageQuery pageQuery);

}
