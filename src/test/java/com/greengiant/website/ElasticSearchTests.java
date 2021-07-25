package com.greengiant.website;

import com.greengiant.website.pojo.model.Article;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import org.zxp.esclientrhl.index.ElasticsearchIndex;
//import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ElasticSearchTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 根据索引列表名称创建索引
     * @throws IOException
     */
    @Test
    public void testCreateIndex() throws IOException {
        String[] indices = new String[]{"cat", "article"};
        try {
            for (int i = 0; i < indices.length; i++) {
                GetIndexRequest request = new GetIndexRequest(indices[i]);
                if (!restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT)) {
                    CreateIndexRequest createIndexRequest = new CreateIndexRequest(indices[i]); //创建一个空索引
                    CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                    System.out.println(createIndexResponse.toString());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取所有索引名称
     */
    @Test
    public void testGetAllIndices() {
        try {
            GetAliasesRequest request = new GetAliasesRequest();
            GetAliasesResponse getAliasesResponse =  restHighLevelClient.indices().getAlias(request,RequestOptions.DEFAULT);
            Map<String, Set<AliasMetaData>> map = getAliasesResponse.getAliases();
            Set<String> indices = map.keySet();
            for (String key : indices) {
                System.out.println(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() {
        IndexRequest indexRequest = new IndexRequest("index");
        indexRequest.id("1"); //文档id
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        indexRequest.source(jsonString, XContentType.JSON);
        RequestOptions requestOptions = new RequestOptions();
        try {
            restHighLevelClient.index(indexRequest, );
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Test
    public void testPageQuery() {
        // todo
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
