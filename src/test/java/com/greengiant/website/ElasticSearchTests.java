package com.greengiant.website;

import com.greengiant.website.pojo.model.Article;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import org.zxp.esclientrhl.index.ElasticsearchIndex;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ElasticSearchTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("cat");//创建一个cat空索引
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.toString());
    }

    // todo 看官网，搞清楚版本对应关系，后续升级心里有底
    // todo 把多条件查询、判断index是否存在等继续写通。把EsClientRHL文档里面提到的功能看看哪些值得写和调试一下
    // todo 搞清楚底层存储结构

    //    @Resource // 为什么要用Resource注解：https://blog.csdn.net/yiye2017zhangmu/article/details/97618748
//    private ElasticsearchTemplate<Article,String> elasticsearchTemplate;
//
//    @Resource
//    private ElasticsearchIndex<Article> elasticsearchIndex;
//
//    @Test
//    public void testCreateIndex() throws Exception {
//        if (!elasticsearchIndex.exists(Article.class)) {
//            System.out.println("Article's ElasticSearch index does not exists.");
//            elasticsearchIndex.createIndex(Article.class);
//        }
//    }

}
