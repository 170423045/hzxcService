package com.hzxc.search.service.impl;

import com.hzxc.framework.domain.course.CoursePub;
import com.hzxc.framework.domain.search.CourseSearchParam;
import com.hzxc.framework.model.response.CommonCode;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.QueryResult;
import com.hzxc.search.service.EsCourseService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.search.service.impl
 * @ClassName: EsCourseServiceImpl
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/30 19:11
 * @Version: 1.0
 */
@Service
public class EsCourseServiceImpl implements EsCourseService {

    @Value("${hzxc.course.source_field}")
    private String sourceFields;
    @Value("${hzxc.course.index}")
    private String index;
    @Value("${hzxc.course.type}")
    private String type;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public QueryResponseResult<CoursePub> list(Integer page, Integer size, CourseSearchParam courseSearchParam) {

        //1.初始化请求对象
        SearchRequest searchRequest=new SearchRequest(index);
        searchRequest.types(type);
        //2.初始化请求构造器
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //2.0定义布尔构造器
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //2.1得到要过滤的字段数组并添加其构造
        String[] fields = sourceFields.split(",");
        searchSourceBuilder.fetchSource(fields,new String[]{});
        //2.2定义关键字进行分词搜索构造器
        if (StringUtils.isNotEmpty(courseSearchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(),
                "name","teachplan","description").minimumShouldMatch("70%");
        //2.2.1提升name字段的boots值
            multiMatchQueryBuilder.field("name",10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }

        //2.3对分类与难度等级进行过滤搜索
        if (StringUtils.isNotEmpty(courseSearchParam.getMt()))
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        if (StringUtils.isNotEmpty(courseSearchParam.getSt()))
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",courseSearchParam.getSt()));
        if (StringUtils.isNotEmpty(courseSearchParam.getGrade()))
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade",courseSearchParam.getGrade()));
        //2.4将类型搜索构造器注入源构造器
        searchSourceBuilder.query(boolQueryBuilder);
        //2.5配置分页参数
        if(page<=0) page=1;
        if (size<=0) size=12;
        int from=(page-1)*size;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        //2.6配置高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.preTags("<font class=\"eslight\">");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field(new HighlightBuilder.Field("name"));
        searchSourceBuilder.highlighter(highlightBuilder);
        //3.将源构造器注入请求对象
        searchRequest.source(searchSourceBuilder);
        //4.发送请求
        SearchResponse response =null;

        try {
            response = restHighLevelClient.search(searchRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //5.处理相应结果
        ArrayList<CoursePub> list=new ArrayList<>();
        QueryResult<CoursePub> queryResult=new QueryResult<>();
        SearchHits responseHits = response.getHits();
        SearchHit[] searchHits = responseHits.getHits();
        queryResult.setTotal(responseHits.getTotalHits());
        for (SearchHit hit:searchHits
        ) {
            CoursePub coursePub=new CoursePub();
            //源对象
            Map<String, Object> objectMap = hit.getSourceAsMap();
            //名称
            String name= (String) objectMap.get("name");

            //高亮字段
            Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
            if (highlightFieldMap!=null){
                HighlightField highlightField = highlightFieldMap.get("name");
                if(highlightField!=null){
                    Text[] fragments = highlightField.fragments();
                    StringBuffer stringBuffer=new StringBuffer();
                    for (Text t:fragments
                    ) {
                        stringBuffer.append(t.toString());
                    }
                    name=stringBuffer.toString();
                }
            }
            if (name!=null) coursePub.setName(name);
            //ID
            String id= (String) objectMap.get("id");
            if (id!=null) coursePub.setId(id);
            //图片路径
            String pic= (String) objectMap.get("pic");
            coursePub.setPic(pic);
            //价格
            Double price=null;
            Double price_old=null;
            try {
                if (objectMap.get("price")!=null)
                    price= (Double) objectMap.get("price");

                //oldprice
                if (objectMap.get("price_old")!=null)
                    price_old=(Double) objectMap.get("price_old");
            }catch (Exception e){
                e.printStackTrace();
            }
            coursePub.setPrice(price);
            coursePub.setPrice_old(price_old);


            list.add(coursePub);

        }
        queryResult.setList(list);

        return new QueryResponseResult<CoursePub>(CommonCode.SUCCESS,queryResult);
    }
}
