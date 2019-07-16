package com.hzxc.manage_cms.mapper;


import com.hzxc.framework.domain.cms.CmsPage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository("cmsPageMapper")
public interface CmsPageMapper extends MongoRepository<CmsPage, String>, QueryByExampleExecutor<CmsPage> {

    CmsPage findByPageName(String s);//方法名必须为“findBy+所操作实体类的字段名”

    CmsPage findBySiteIdAndPageNameAndPageWebPath(String siteId,  String pageName,String pageWebPath);
}
