package com.hzxc.cms_manage_client.service;

import com.hzxc.cms_manage_client.mapper.CmsPageMapper;
import com.hzxc.cms_manage_client.mapper.CmsSiteMapper;
import com.hzxc.framework.domain.cms.CmsPage;
import com.hzxc.framework.domain.cms.CmsSite;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @ProjectName: bwqcService01
 * @Package: com.bwqc.cms_manage_client.service
 * @ClassName: CmsClientService
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/11 22:31
 * @Version: 1.0
 */
@Service
public class CmsClientService  {
    private static final Logger LOGGER= LoggerFactory.getLogger(CmsClientService.class);
    @Autowired
    CmsSiteMapper cmsSiteMapper;
    @Autowired
    CmsPageMapper cmsPageMapper;
    @Autowired
    GridFSBucket gridFSBucket;
    @Autowired
    GridFsTemplate gridFsTemplate;

    /**
     *
     * @param id 页面id
     *           将页面下载到指定服务器
     */
    public void savePageToServerPath(String id){
        if(id==null){
            LOGGER.error("pageId is null ,pageId:{}",id);
            return;
        }
        //获取页面信信息
        CmsPage cmsPage=getPageById(id);


        String htmlFileId = cmsPage.getHtmlFileId();
        //获取html文件读取流
        InputStream stream = getStream(htmlFileId);
        if(stream==null){
            LOGGER.error("getFileById InputStream is null ,htmlFileId:{}",htmlFileId);
            return;
        }
        //将文件保存到指定服务器的物理路径下
        //根据站点id获得站点信息
        Optional<CmsSite> optional = cmsSiteMapper.findById(cmsPage.getSiteId());
        if(!optional.isPresent()){
            LOGGER.error("CmsSite is null ,htmlFileId:{}",htmlFileId);
            return;
        }
        CmsSite cmsSite = optional.get();
        //站点物理路径
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        //页面路径
        String pagePhysicalPath = cmsPage.getPagePhysicalPath();
        //文件名
        String pageName = cmsPage.getPageName();
        //文件全路径
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream=new FileOutputStream(sitePhysicalPath+pagePhysicalPath+pageName);
            IOUtils.copy(stream,fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*

    * 根据id获取页面信息
    *
    * */
    private CmsPage getPageById(String id) {
        Optional<CmsPage> cmsPage = cmsPageMapper.findById(id);
        if(cmsPage.isPresent()){
            return cmsPage.get();
        }
        return null;
    }
    /**
     *
     * 获取html文件下载流
     *
     * */
    private InputStream getStream(String htmlFileId){
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        //获取下载流
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //获得inputStreamResource
        GridFsResource gridFsResource=new GridFsResource(gridFSFile,gridFSDownloadStream);
        //返回输入流
        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
