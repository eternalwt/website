package com.greengiant.website.component;

import com.greengiant.website.WebsiteApplication;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// todo 写通BulkResponse，比较GetResponse、searchRequest、BulkResponse
// todo 排序（排序应该5分钟就能搞定）
// todo QueryBuilders.matchAllQuery：https://blog.csdn.net/geloin/article/details/8926735

@ExtendWith(SpringExtension.class)
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
            GetAliasesResponse getAliasesResponse =  restHighLevelClient.indices().getAlias(request, RequestOptions.DEFAULT);
            Map<String, Set<AliasMetadata>> map = getAliasesResponse.getAliases();
            Set<String> indices = map.keySet();
            for (String key : indices) {
                System.out.println(key);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testInsertDoc() {
        // todo IndexRequest的更多初始化方式：https://blog.csdn.net/hellow0rd/article/details/108192964
        IndexRequest indexRequest = new IndexRequest("cat");
        indexRequest.id("1"); //文档id
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
    public void testBulkInsertDoc() throws IOException {
        BulkRequest request = new BulkRequest();

        IndexRequest indexRequest1 = new IndexRequest("cat");
        indexRequest1.id("11"); //文档id
        String jsonString1 = "{" +
                "\"user\":\"gao1\"," +
                "\"postDate\":\"2021-16-16\"," +
                "\"message\":\"该底座通过 60.5GHz 无线频率与设备进行通信。该设备在 9 月份的美国联邦通信委员会文件中首次被发现，尽管其具体用途不明\"" +
                "}";
        indexRequest1.source(jsonString1, XContentType.JSON);

        IndexRequest indexRequest2 = new IndexRequest("cat");
        indexRequest2.id("12"); //文档id
        String jsonString2 = "{" +
                "\"user\":\"gao2\"," +
                "\"postDate\":\"2021-10-16\"," +
                "\"message\":\"巴西的监管文件包括该设备的一些图片，以及一个演示 Apple Watch 如何在其中安装的图形。\"" +
                "}";
        indexRequest2.source(jsonString2, XContentType.JSON);

        IndexRequest indexRequest3 = new IndexRequest("cat");
        indexRequest3.id("13"); //文档id
        String jsonString3 = "{" +
                "\"user\":\"gao3\"," +
                "\"postDate\":\"2021-10-16\"," +
                "\"message\":\"目前还不完全清楚该设备的具体诊断目的，但它显然是一个供苹果技术人员内部使用的产品。\"" +
                "}";
        indexRequest3.source(jsonString3, XContentType.JSON);

        IndexRequest indexRequest4 = new IndexRequest("cat");
        indexRequest4.id("14"); //文档id
        String jsonString4 = "{" +
                "\"user\":\"gao4\"," +
                "\"postDate\":\"2021-10-16\"," +
                "\"message\":\"诊断底座能以每秒约 200 兆比特的速度与Apple Watch通信。这还不到 USB 2.0 最高速度的一半，也远不如 Wi-Fi 快。\"" +
                "}";
        indexRequest4.source(jsonString4, XContentType.JSON);

        IndexRequest indexRequest5 = new IndexRequest("cat");
        indexRequest5.id("15"); //文档id
        String jsonString5 = "{" +
                "\"user\":\"gao5\"," +
                "\"postDate\":\"2021-10-16\"," +
                "\"message\":\"Apple Watch Series 7 型号配备了一个新模块，可以实现 60.5GHz 无线数据传输。这个模块在苹果网站上没有宣传，很可能只供苹果内部使用，与此同时这一型号缺少一个隐藏的诊断端口，该端口位于之前所有Apple Watch型号的底部表带槽中。苹果在维修Apple Watch时使用该端口进行诊断，比如通过有线连接用特殊工具恢复watchOS。\"" +
                "}";
        indexRequest5.source(jsonString5, XContentType.JSON);

        request.add(indexRequest1);// todo 为啥这个没有插入进去？
        request.add(indexRequest2);
        request.add(indexRequest3);
        request.add(indexRequest4);
        request.add(indexRequest5);
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        if (response != null && response.getItems() != null) {
            System.out.println(response.getItems().length);
        }
    }

    // todo 1.多条件、模糊查询；3.如何反序列化成方便使用的对象？

    @Test
    public void testGetById() throws IOException {
        GetRequest getRequest = new GetRequest("cat");
        getRequest.id("1");
        String[] includes = new String[]{"*"};// 包含的列（所有列）
        String[] excludes = Strings.EMPTY_ARRAY;// 不包含的列
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);
        GetResponse response = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        System.out.println(response.getSource().keySet().toString());
        System.out.println(response.getSource().values().toString());
    }

    // todo MatchQueryBuilder RangeQueryBuilder SearchSourceBuilder【看懂类体系】
    // todo 多条件查询(QueryBuilders.multiMatchQuery?)、模糊查询、ik分词查询（现在还不行） 模糊查询+分词查询
    // todo 分页查询

    @Test
    public void testSearchTermQuery1() throws IOException {
        SearchRequest searchRequest = new SearchRequest("cat");
        //searchRequest.types("type");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(QueryBuilders.termsQuery("message", "巴西"));
        // 字段过滤：参数代表包含和不包含的字段
        searchSourceBuilder.fetchSource(new String[]{"*"}, new String[]{});


        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的记录总数
        long totalHits = hits.getTotalHits().value;
        System.out.println(totalHits);
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String id = (String) sourceAsMap.get("user");
            System.out.println(id);
        }
    }

    @Test
    public void testSearchTermQuery2() throws IOException {
        SearchRequest searchRequest = new SearchRequest("cat");
        //searchRequest.types("type");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("message", "Seryes"));
        // 字段过滤：参数代表包含和不包含的字段
        searchSourceBuilder.fetchSource(new String[]{"*"}, new String[]{});
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 结果
        SearchHits hits = searchResponse.getHits();
        // 匹配到的记录总数
        long totalHits = hits.getTotalHits().value;
        System.out.println(totalHits);
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            String id = (String) sourceAsMap.get("user");
            System.out.println(id);
        }
    }

    @Test
    public void testSearchMatchQuery1() throws IOException {
        // todo matchPhraseQuery
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

    }

    @Test
    public void testIKQuery() throws IOException {

    }

    @Test
    public void testAnalyzeRequest() {
//        AnalyzeRequest analyzeRequest = new AnalyzeRequest();
//
//        analyzeRequest.text("我爱中国","我喜欢中国"); // 设置需要分词的中文字
//        analyzeRequest.analyzer("ik_smart");       // 设置使用什么分词器  也可以使用 ik_max_word 它是细粒度分词
//
//        try {
//            AnalyzeResponse analyzeResponse = restHighLevelClient.indices().analyze(analyzeRequest, RequestOptions.DEFAULT);
//            List<AnalyzeResponse.AnalyzeToken> tokens = analyzeResponse.getTokens(); // 获取所有分词的内容
//            // 使用Java 8 语法获取分词内容
//            tokens.forEach(token -> {
//                // 过滤内容，如果文字小于2位也过滤掉
//                if (!"<NUM>".equals(token.getType()) || token.getTerm().length() > 2) {
//                    String term = token.getTerm(); // 分词内容
////                    System.out.prinlnt(term)
//                }
//            });
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
