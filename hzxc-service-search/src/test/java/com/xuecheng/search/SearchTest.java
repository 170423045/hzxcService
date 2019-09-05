package com.xuecheng.search;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: hzxcService
 * @Package: com.xuecheng.search
 * @ClassName: SearchTest
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/19 10:30
 * @Version: 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchTest {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    RestClient restClient;

    /**
     * 查询所有数据：matchAllQuery
     *
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void testDelete() throws IOException, ParseException {
        //搜索请求对象
        SearchRequest searchRequest=
                new SearchRequest("xc_course");
        searchRequest.types("doc");
        //搜索源构造对象
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //设置搜索方式
        //matchAllQuery全文搜索
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //要过滤与搜索的源字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","timestamp"},new String[]{});
        //在请求对象中注入源对象
        searchRequest.source(searchSourceBuilder);
        //发送请求获得相应结果
        SearchResponse response = restHighLevelClient.search(searchRequest);
        //从响应结果中获取查询结果
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        //将查询到的日期格式转换
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy‐MM‐dd HH:mm:mm");
        //遍历查询结果
        for (SearchHit hit:searchHits
             ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            int id = hit.docId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");
            Date timestamp = simpleDateFormat.parse((String)sourceAsMap.get("timestamp"));
            System.out.println("index:"+index);
            System.out.println("type:"+type);
            System.out.println("id:"+id);
            System.out.println("score:"+score);
            System.out.println("sourceAsString:"+sourceAsString);
            System.out.println("name:"+name);
            System.out.println("description:"+description);
            System.out.println("studymodel:"+studymodel);
            System.out.println("timestamp:"+timestamp);
        }

    }

    /**
     * 分页查询
     * searchSourceBuilder.from(0);
     * searchSourceBuilder.size(1);
     *
     * @throws Exception
     */
    @Test
    public void testPageSearch() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            int id = hit.docId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

            System.out.println("index:"+index);
            System.out.println("type:"+type);
            System.out.println("id:"+id);
            System.out.println("score:"+score);
            System.out.println("sourceAsString:"+sourceAsString);
            System.out.println("name:"+name);
            System.out.println("description:"+description);
            System.out.println("studymodel:"+studymodel);

        }
    }

    /**
     * termQuery
     * 精确查询
     * @throws Exception
     */
    @Test
    public void testTermSearch() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name","spring1"));
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            int id = hit.docId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

            System.out.println("index:"+index);
            System.out.println("type:"+type);
            System.out.println("id:"+id);
            System.out.println("score:"+score);
            System.out.println("sourceAsString:"+sourceAsString);
            System.out.println("name:"+name);
            System.out.println("description:"+description);
            System.out.println("studymodel:"+studymodel);

        }
    }

    /**
     * 根据ID搜索
     * termsQuery：可携带多个参数
     *
     * @throws Exception
     */
    @Test
    public void testTermsSearch() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //初始化要搜索的id的数组
        String[] ids=new String[]{"1","2"};
        searchSourceBuilder.query(QueryBuilders.termsQuery("_id",ids));
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            String id = hit.getId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

        }
    }
    /**
     * matchquery
     * .minimumShouldMatch("80%")：至少有80%的词在查询中出现
     * @throws Exception
     */
    @Test
    public void testMatchSearch() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //过滤字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});
        //matchQuery
//        searchSourceBuilder.query(QueryBuilders.matchQuery("description","spring开发").operator(Operator.AND));
        searchSourceBuilder.query(QueryBuilders.matchQuery("description","前台页面开发框架").minimumShouldMatch("80%"));


        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            String id = hit.getId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

        }
    }
    /**
     * multiMatchQuery：自定义各字段权重
     * .field("name",10)
     * @throws Exception
     */
    @Test
    public void multiMatchQuery() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //过滤字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});
        //matchQuery
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring css","description","name")
                .minimumShouldMatch("50%")
                .field("name",10));


        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            String id = hit.getId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

        }
    }
    /**
     * BoolQueryBuilder：布尔查询
     *
     * @throws Exception
     */
    @Test
    public void boolQuery() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //过滤字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});

        //布尔查询（同时满足多种查询条件）
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring框架", "name", "description").minimumShouldMatch("50%");
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");

        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.must(termQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            String id = hit.getId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

        }
    }
    /**
     * filter
     *
     * @throws Exception
     */
    @Test
    public void filterQuery() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //过滤字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});

        //布尔查询（同时满足多种查询条件）
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring框架", "name", "description").minimumShouldMatch("50%");


        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
//        boolQueryBuilder.must(multiMatchQueryBuilder);
        boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel","201001"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(70).lte(100));

        searchSourceBuilder.query(boolQueryBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            String id = hit.getId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

        }
    }
    /**
     * sort
     *
     * @throws Exception
     */
    @Test
    public void sortQuery() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //过滤字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel"},new String[]{});

        //布尔查询（同时满足多种查询条件）
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring框架", "name", "description").minimumShouldMatch("50%");


        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();

        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));

        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.sort("studymodel", SortOrder.DESC);
        searchSourceBuilder.sort("price", SortOrder.ASC);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            String id = hit.getId();
            //获取分数
            float score = hit.getScore();
            //获取源已map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

        }
    }
    /**
     * 高亮
     *
     * @throws Exception
     */
    @Test
    public void highLightQuery() throws Exception {
        //创建查询请求
        SearchRequest searchRequest=new SearchRequest("xc_course");
        searchRequest.types("doc");
        //构造搜索构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //过滤字段
        searchSourceBuilder.fetchSource(new String[]{"name","studymodel","description"},new String[]{});

        //布尔查询（同时满足多种查询条件）
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("开发", "name", "description").minimumShouldMatch("50%");


        BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();

        boolQueryBuilder.filter(multiMatchQueryBuilder);
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));

        searchSourceBuilder.query(boolQueryBuilder);
        //排序显示
        searchSourceBuilder.sort("studymodel", SortOrder.DESC);
        searchSourceBuilder.sort("price", SortOrder.ASC);
        //高亮显示
        HighlightBuilder highlightBuilder=
                new HighlightBuilder();
        highlightBuilder.preTags("<tag>");
        highlightBuilder.postTags("</tag>");
        highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
        highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
        searchSourceBuilder.highlighter(highlightBuilder);

        //发送请求进行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit:searchHits
        ) {
            //获取索引库
            String index = hit.getIndex();
            //获取表名
            String type = hit.getType();
            //获取组件
            String id = hit.getId();
            //获取分数
            float score = hit.getScore();
            //获取源以map返回
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //获取源已String返回
            String sourceAsString = hit.getSourceAsString();
            String name = (String) sourceAsMap.get("name");
            String description = (String) sourceAsMap.get("description");
            String studymodel = (String) sourceAsMap.get("studymodel");

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(highlightFields!=null){
                HighlightField nameHighlightField = highlightFields.get("name");
                HighlightField descriptionHighlightField = highlightFields.get("description");
                if (nameHighlightField!=null){
                    Text[] fragments = nameHighlightField.getFragments();
                    StringBuffer stringBuffer=new StringBuffer();
                    for (Text t:fragments
                    ) {
                        stringBuffer.append(t.string());
                    }
                    name=stringBuffer.toString();
                }
                if (descriptionHighlightField!=null){
                    Text[] fragments = descriptionHighlightField.getFragments();
                    StringBuffer stringBuffer=new StringBuffer();
                    for (Text t:fragments
                    ) {
                        stringBuffer.append(t.string());
                    }
                    description=stringBuffer.toString();
                }

            }
            System.out.println(name);
            System.out.println(description);

        }
    }
}
