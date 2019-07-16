package com.hzxc.manage_cms.mapper;

import com.hzxc.framework.domain.cms.CmsPage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageMapper cmsPageMapper;

    @Test
    public void testFindAll(){
        List<CmsPage> all = cmsPageMapper.findAll();
//        CmsPage cmsPage=new CmsPage();
//        cmsPage.setPageName("第二测试");
//        cmsPageMapper.save(cmsPage);
        System.out.println(all);

    }

    //分页查询
    @Test
    public void testFindPage(){
        //分页参数
        int page = 1;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageMapper.findAll(pageable);
        System.out.println(all.getContent());
    }

    /**
     * 根据条件查询
     */
    @Test
    public void testFindByExample(){
        //分页参数
        int page = 0;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);

        //定义条件封装类
        CmsPage cmsPage=new CmsPage();
//        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");

        cmsPage.setPageAliase("轮播");
//        cmsPage.setPageName("4028858162e0bc0a0162e0bfdf1a0000.html");
        //条件匹配器
        ExampleMatcher matcher=ExampleMatcher.matching();
        matcher=matcher
                .withMatcher("pageAliase", UntypedExampleMatcher.GenericPropertyMatchers.contains()).withIgnoreNullValues();

        Example<CmsPage> cmsPageExample=Example.of(cmsPage,matcher);

        Page<CmsPage> all = cmsPageMapper.findAll(cmsPageExample,pageable);
        List<CmsPage> content = all.getContent();
//        List<CmsPage> content = all.getContent();
        System.out.println(content);

    }

    //分页查询
    @Test
    public void testUpdate(){
        Optional<CmsPage> op = cmsPageMapper.findById("5b4b1d8bf73c6623b03f8cec");
        if(op.isPresent()){
            CmsPage cmsPage = op.get();
            System.out.println(cmsPage);
//            cmsPage.setPageName("test");
//            cmsPageMapper.save(cmsPage);
        }
    }

    //修改
    /*@Test
    public void testUpdate() {
        //查询对象
        Optional<CmsPage> optional = cmsPageRepository.findById("5b4b1d8bf73c6623b03f8cec");
        if(optional.isPresent()){
            CmsPage cmsPage = optional.get();
            //设置要修改值
            cmsPage.setPageAliase("test01");
            //...
            //修改
            CmsPage save = cmsPageRepository.save(cmsPage);
            System.out.println(save);
        }

    }*/

    //根据页面名称查询
    @Test
    public void testfindByPageName(){
        CmsPage cmsPage = cmsPageMapper.findByPageName("index.html");
        System.out.println(cmsPage);
    }

    @Test
    public void testFindAllByExample() {
        //分页参数
        int page = 0;//从0开始
        int size = 10;
        Pageable pageable = PageRequest.of(page,size);

        //条件值对象
        CmsPage cmsPage= new CmsPage();
        //要查询5a751fab6abb5044e0d19ea1站点的页面
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        //设置模板id条件
//        cmsPage.setTemplateId("5ad9a24d68db5239b8fef199");
        //设置页面别名
       // cmsPage.setPageAliase("轮播");
        //条件匹配器
//        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
//        exampleMatcher = exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("siteId", ExampleMatcher.GenericPropertyMatchers.contains());
        //ExampleMatcher.GenericPropertyMatchers.contains() 包含关键字
//        ExampleMatcher.GenericPropertyMatchers.startsWith()//前缀匹配
        //定义Example
        Example<CmsPage> example = Example.of(cmsPage,exampleMatcher);
//        Page<CmsPage> all = cmsPageMapper.findAll(example, pageable);
        List<CmsPage> content = cmsPageMapper.findAll(example);
//        List<CmsPage> content = all.getContent();
        System.out.println(content);
    }

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void restTemplate(){
        Map map = restTemplate.getForObject("http://localhost:31001/cms/config/getModel/5a791725dd573c3574ee333f", Map.class);
        System.out.println(map);
    }
}
