package com.greengiant.website;

import com.greengiant.website.pojo.model.Article;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
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
    public void testInsertDoc() {
        IndexRequest indexRequest = new IndexRequest("cat");
        indexRequest.id("2"); //文档id
        String jsonString = "{" +
                "\"user\":\"gao\"," +
                "\"postDate\":\"2013-01-31\"," +
                "\"message\":\"trying out Elasticsearch 2\"" +
                "}";
        indexRequest.source(jsonString, XContentType.JSON);

        try {
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(indexResponse.getIndex());
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void testGetById1() throws IOException {
        // todo 1.搞清楚incldes、excludes；2.多条件、模糊查询；3.如何反序列化成方便使用的对象？
        GetRequest getRequest = new GetRequest("cat");
        getRequest.id("1");
//        String[] includes = new String[]{"*", "birthday"};
        String[] includes = new String[]{"*"};// 包含的列
        String[] excludes = Strings.EMPTY_ARRAY;// 不包含的列
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println(response.getSource().keySet().toString());
        System.out.println(response.getSource().values().toString());
    }

    @Test
    public void testGetById2() throws IOException {
        SearchRequest searchRequest = new SearchRequest("cat");
        //searchRequest.types("type");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] ids = new String[]{"d136fc82-f458-4ba2-96a8-e83c4f621571", "6051bcaf-d587-44cd-86ba-b4c5c39da08f"};
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id", ids));
        //設定源欄位過慮,第一個引數結果集包括哪些欄位，第二個參數列示結果集不包括哪些欄位
//        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜尋請求物件中設定搜尋源
        searchRequest.source(searchSourceBuilder);
        //執行搜尋,向ES發起http請求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //搜尋結果
        SearchHits hits = searchResponse.getHits();
        //匹配到的總記錄數
        long totalHits = hits.getTotalHits().value;
        System.out.println(totalHits);
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String id = (String) sourceAsMap.get("id");
            System.out.println(id);
        }
    }

    @Test
    public void termQuery() throws IOException {
        SearchRequest searchRequest = new SearchRequest("index");
        //searchRequest.types("type");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.termQuery("rowkey", "eb3cddde-f745-4360-bd7c-c271e2087a31"));
        //設定源欄位過慮,第一個引數結果集包括哪些欄位，第二個參數列示結果集不包括哪些欄位
//        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
        //向搜尋請求物件中設定搜尋源
        searchRequest.source(searchSourceBuilder);
        //執行搜尋,向ES發起http請求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //搜尋結果
        SearchHits hits = searchResponse.getHits();
        //匹配到的總記錄數
        long totalHits = hits.getTotalHits().value;
        System.out.println(totalHits);
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String id = (String) sourceAsMap.get("id");
            System.out.println(id);
        }
    }

    @Test
    public void searchByPage() throws IOException {
        SearchRequest searchRequest = new SearchRequest("cat");
        //searchRequest.types("ads_peer_info_community_type");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        int page = 1;
        int size = 2;
        int from = (page - 1) * size;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 结果
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits().value;
        System.out.println(totalHits);
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String name= (String) sourceAsMap.get("user");
            System.out.println(name);
        }
    }

    // 复合条件查询、多id查询：https://iter01.com/566332.html
    // fuzzyQuery、matchQuery(分词)：http://jvm123.com/2020/08/spring-zhong-shi.html


    // todo 模糊查询

    @Test
    public void testPageQuery() {
        // todo
    }

    @Test
    public void testConditionalQuery() {
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
