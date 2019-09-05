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
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
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
public class IndexTest {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Autowired
    RestClient restClient;

    //删除索引库
    @Test
    public void testDelete() throws IOException {
        //创建删除请求对象
        DeleteIndexRequest deleteIndexRequest=new DeleteIndexRequest("xc_course");
        //操作索引客户端
        IndicesClient indicesClient = restHighLevelClient.indices();
        //发送删除请求
        DeleteIndexResponse delete = indicesClient.delete(deleteIndexRequest);
        //得到相应结果
        System.out.println(delete.isAcknowledged());

    }

    //创建索引库
    @Test
    public void testCreate() throws IOException {
        //创建创建请求对象
        CreateIndexRequest createIndexRequest=new CreateIndexRequest("xc_course");
        createIndexRequest.settings(Settings.builder()
                .put("number_of_shards",1)
                .put("number_of_replicas",0));
        createIndexRequest.mapping("doc",
                "{ \"properties\": { \"description\": { \"type\": \"text\", \"analyzer\": \"ik_max_word\", \"search_analyzer\": \"ik_smart\" },\"name\": { \"type\": \"text\", \"analyzer\": \"ik_max_word\", \"search_analyzer\": \"ik_smart\" },\"pic\":{ \"type\":\"text\", \"index\":false }, \"price\": { \"type\": \"float\" },\"studymodel\": { \"type\": \"keyword\" },\"timestamp\": { \"type\": \"date\", \"format\": \"yyyy‐MM‐dd HH:mm:ss||yyyy‐MM‐dd||epoch_millis\" } } }",
                XContentType.JSON);
        //操作索引客户端
        IndicesClient indicesClient = restHighLevelClient.indices();
        //发送创建请求
        CreateIndexResponse createIndexResponse = indicesClient.create(createIndexRequest);
        //得到相应结果
        System.out.println(createIndexResponse.isAcknowledged());

    }

    //添加文档操作
    @Test
    public void addDoc() throws IOException {
        //准备json数据
        Map<String,Object> jsonMap=new HashMap<>();
        jsonMap.put("name","spring开发基础");
        jsonMap.put("description","spring 在java领域非常流行，java程序员都在用。");
        jsonMap.put("studymodel",201001);
        SimpleDateFormat format=
                new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        jsonMap.put("timestamp","2018‐02‐24 19:11:35");
        jsonMap.put("price",88.6f);
        jsonMap.put("pic","group1/M00/00/00/wKhlQFs6RCeAY0pHAAJx5ZjNDEM428.jpg");
        //创建索引请求对象
        IndexRequest indexRequest=new IndexRequest("xc_course","doc");
        //放入json数据
        indexRequest.source(jsonMap);
        //发送请求
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest);
        DocWriteResponse.Result result = indexResponse.getResult();
        System.out.println(result);
    }

    //查询文档操作
    @Test
    public void getDoc() throws IOException {
       //获得查询请求对象
        GetRequest getRequest=
                new GetRequest("xc_course","doc","g0KQCGwBtH-McqdpEgly");

        //发送查询请求
        GetResponse getResponse = restHighLevelClient.get(getRequest);

        //获取查询结果
        Map<String, Object> source = getResponse.getSource();
        System.out.println(source);
    }

    //修改文档文档操作
    @Test
    public void updataDoc() throws IOException {
        //准备要更新的字段内容
        Map<String,String> map=new HashMap<>();
        map.put("name","spring cloud实战内容");
        //获得更新请求对象
        UpdateRequest updateRequest=new UpdateRequest("xc_course","doc","g0KQCGwBtH-McqdpEgly");
        updateRequest.doc(map);
        //发送更新请求
        UpdateResponse response = restHighLevelClient.update(updateRequest);
        RestStatus status = response.status();
        System.out.println(status);
    }
    //删除文档文档操作
    @Test
    public void deleteDoc() throws IOException {
        //要删除文档的id
        String id="g0KQCGwBtH-McqdpEgly";
        DeleteRequest deleteRequest=new DeleteRequest("xc_course","doc",id);
        DeleteResponse response = restHighLevelClient.delete(deleteRequest);
        RestStatus status = response.status();
        System.out.println(status);
    }

}
