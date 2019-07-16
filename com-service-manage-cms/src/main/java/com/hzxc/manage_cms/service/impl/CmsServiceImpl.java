package com.hzxc.manage_cms.service.impl;

import com.alibaba.fastjson.JSON;
import com.hzxc.framework.domain.cms.CmsPage;
import com.hzxc.framework.domain.cms.CmsSite;
import com.hzxc.framework.domain.cms.CmsTemplate;
import com.hzxc.framework.domain.cms.request.QueryPageRequest;
import com.hzxc.framework.domain.cms.response.CmsCode;
import com.hzxc.framework.domain.cms.response.CmsPageResult;
import com.hzxc.framework.exception.ExceptionCast;
import com.hzxc.framework.model.response.CommonCode;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.QueryResult;
import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.manage_cms.config.RabbitMQConfig;
import com.hzxc.manage_cms.mapper.CmsPageMapper;
import com.hzxc.manage_cms.mapper.CmsSiteMapper;
import com.hzxc.manage_cms.mapper.CmsTemplateMapper;
import com.hzxc.manage_cms.service.CmsService;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.manage_cms.service.impl
 * @ClassName: CmsService
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/5 13:23
 * @Version: 1.0
 */
@Service
public class CmsServiceImpl implements CmsService {


    //页面信息访问层
    @Autowired
    @Qualifier("cmsPageMapper")
    CmsPageMapper cmsPageMapper;

    //站点信息访问层
    @Autowired
    @Qualifier("cmsSiteMapper")
    CmsSiteMapper cmsSiteMapper;

    //模板访问层
    @Autowired
    CmsTemplateMapper cmsTemplateMapper;

    //客户端模板对象
    @Autowired
    RestTemplate restTemplate;

    //文件储存数据库模板对象
    @Autowired
    GridFsTemplate gridFsTemplate;

    //获取文件下载流
    @Autowired
    GridFSBucket gridFSBucket;

    //发送队列消息
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     *  分页查询页面信息
     * @param page 查询当前页
     * @param size 查询数据条数
     * @param queryPageRequest 查询条件
     * @return
     */
    @Override
    public QueryResponseResult findPageList
            (Integer page, Integer size, QueryPageRequest queryPageRequest) {
        CmsPage cmsPage=new CmsPage();
        String condition=null;
        //定义条件匹配器
        ExampleMatcher exampleMatcher=ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());

        //如果有查询条件则将传入的查询条件封装进CmsPage对象
        if(queryPageRequest!=null){
            if(StringUtils.isNotEmpty(condition=(queryPageRequest.getPageAliase()))){
                cmsPage.setPageAliase(condition);
            }
            if(StringUtils.isNotEmpty(condition=(queryPageRequest.getPageName()))){
                cmsPage.setPageName(condition);
            }
            if(StringUtils.isNotEmpty(condition=(queryPageRequest.getTemplateId()))){
                cmsPage.setTemplateId(condition);
            }
            if(StringUtils.isNotEmpty(condition=(queryPageRequest.getSiteId()))){
                cmsPage.setSiteId(condition);
            }

        }
        //定义Example
        Example<CmsPage> example=Example.of(cmsPage,exampleMatcher);


        if(page<=0)
            page=1;

        page-=1;
        //定义分页参数
        Pageable pageable= PageRequest.of(page,size);

        //进行查询
        Page<CmsPage> all = cmsPageMapper.findAll(example,pageable);

        //对查到的结果进行封装
        QueryResult<CmsPage> queryResult=new QueryResult<>();
        queryResult.setList(all.getContent());
        queryResult.setTotal(all.getTotalElements());



        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }


    /**
     * 查询所有列表信息
     * @return
     */
    @Override
    public QueryResponseResult findSiteList() {
        List<CmsSite> all = cmsSiteMapper.findAll();

        QueryResult<CmsSite> queryResult=new QueryResult<>();
        queryResult.setList(all);

        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    @Override
    public CmsPageResult add(CmsPage cmsPage) {
        //根据.siteId,pageWebPath,pageName判断页面是否重复
        CmsPage cmsPage1 = cmsPageMapper
                .findBySiteIdAndPageNameAndPageWebPath(cmsPage.getSiteId(), cmsPage.getPageName(), cmsPage.getPageWebPath());

        if(cmsPage1!=null){//如果无重复站点则进行添加并返回成功信息
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        cmsPage.setPageId(null);
        CmsPage save = cmsPageMapper.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS,save);
//        return  new CmsPageResult(CommonCode.FAIL,null);
    }

    @Override
    public CmsPage findById(String id) {
        Optional<CmsPage> optional = cmsPageMapper.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     *
     * 页面信息修改业务
     * @param id
     * @param cmsPage
     * @return
     */
    @Override
    public CmsPageResult edit(String id, CmsPage cmsPage) {
        CmsPage one = this.findById(id);
        if(one!=null){
            //准备更新数据
            //设置要修改的数据
            //更新模板id
            one.setTemplateId(cmsPage.getTemplateId());
            //更新所属站点
            one.setSiteId(cmsPage.getSiteId());
            //更新页面别名
            one.setPageAliase(cmsPage.getPageAliase());
            //更新页面名称
            one.setPageName(cmsPage.getPageName());
            //更新访问路径
            one.setPageWebPath(cmsPage.getPageWebPath());
            //更新物理路径
            one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
            //更新数据访问接口
            one.setDataUrl(cmsPage.getDataUrl());
            cmsPageMapper.save(one);
            return new CmsPageResult(CommonCode.SUCCESS,cmsPage);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }

    /**
     * 根据id删除页面信息
     * @param id
     * @return
     *
     */
    @Override
    public ResponseResult deleteById(String id) {
        CmsPage id1 = this.findById(id);
        if (id1!=null){
        cmsPageMapper.deleteById(id);
        return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     *
     * 静态化页面
     *
     * 1.获取数据模型
     *
     * 2.获取模板
     *
     * 3.页面静态化
     *
     * */
    public String getHtml(String id){
        //获取数据模型
        Map model = this.getModelData(id);

        String template = this.getPageTemplate(id);
        if (template==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String pageHtml = this.getPageHtml(template, model);
        if (pageHtml==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return pageHtml;
    }

    @Override
    public ResponseResult postPage(String id) {

        CmsPage cmsPage = findById(id);
        if (cmsPage==null){
            ExceptionCast.cast(CmsCode.CMS_FIND_PAGEISNULL);
        }
        //执行对页面进行静态化
        String htmlContent = this.getHtml(id);
        //将静态化页面存到GridFS中
        cmsPage = savaHtml(cmsPage, htmlContent);
        //发布消息
        this.sendPostpage(cmsPage);

        return new ResponseResult(CommonCode.SUCCESS);
    }
    /**
     *
     * 将静态化的html文件保存到GridFS中
     *
     * */
    private CmsPage savaHtml(CmsPage cmsPage, String htmlContent) {
        InputStream inputStream =null;
        try {
            inputStream = IOUtils.toInputStream(htmlContent, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectId objectId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
        cmsPage.setHtmlFileId(objectId.toHexString());
        cmsPageMapper.save(cmsPage);
        return cmsPage;
    }

    /**
     *
     * 想消息队列发送发布信息
     * @param cmsPage
     *
     */
    public void sendPostpage(CmsPage cmsPage){
        Map<String,String> map=new HashMap<>();
        map.put("pageId",cmsPage.getPageId());
        String msg = JSON.toJSONString(map);

        rabbitTemplate.convertAndSend(RabbitMQConfig.EX_ROUTING_CMS_POSTPAGE,cmsPage.getSiteId(),msg);

    }
    /**
     *
     *
     * 1.获取数据模型
     *
     *
     * */
    public Map getModelData(String id){
        CmsPage cmsPage = this.findById(id);
        if (cmsPage==null){
            ExceptionCast.cast(CmsCode.CMS_FIND_PAGEISNULL);
        }
        String dataUrl = cmsPage.getDataUrl();
        if(dataUrl==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        Map map = restTemplate.getForObject(dataUrl, Map.class);
        if (map==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        return map;
    }
    /**
     *
     * 2.获取页面模板
     *
     * */
    public String getPageTemplate(String id){
        CmsPage cmsPage = this.findById(id);
        if (cmsPage==null){
            ExceptionCast.cast(CmsCode.CMS_FIND_PAGEISNULL);
        }
        String templateId = cmsPage.getTemplateId();
        if(templateId==null){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        Optional<CmsTemplate> optionalCmsTemplate = cmsTemplateMapper.findById(templateId);
        if (!optionalCmsTemplate.isPresent()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        CmsTemplate cmsTemplate = optionalCmsTemplate.get();
        String templateFileId = cmsTemplate.getTemplateFileId();
        //根据ID查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
        //获取下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //获取流
        GridFsResource gridFsResource=new GridFsResource(gridFSFile,gridFSDownloadStream);
        //将流中信息输出
        try {
            String content = IOUtils.toString(gridFsResource.getInputStream(),"utf-8");
            return content;
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    /**
     *
     * 3.页面静态化
     *
     * */
    private String getPageHtml(String template, Map model) {
        //Freemarker页面静态化配置类
        Configuration configuration=new Configuration(Configuration.getVersion());
        //获取模板加载器
        StringTemplateLoader templateLoader=new StringTemplateLoader();
        templateLoader.putTemplate("template",template);
        //获取模板对象
        configuration.setTemplateLoader(templateLoader);
        try {
            Template template1 = configuration.getTemplate("template");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
        } catch (IOException e) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        } catch (TemplateException e) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return null;
    }
}
