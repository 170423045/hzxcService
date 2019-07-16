package com.hzxc.manage_cms.service;

import com.hzxc.framework.domain.cms.CmsPage;
import com.hzxc.framework.domain.cms.request.QueryPageRequest;
import com.hzxc.framework.domain.cms.response.CmsPageResult;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public interface CmsService {
    /**
     *
     * @param page 查询当前页
     * @param size 查询数据条数
     * @param queryPageRequest 查询条件
     * @return
     */
    public QueryResponseResult findPageList( Integer page, Integer size, QueryPageRequest queryPageRequest);

    /**
     *
     * @return
     */
    QueryResponseResult findSiteList();

    CmsPageResult add(CmsPage cmsPage);

    CmsPage findById(String id);

    CmsPageResult edit(String id, CmsPage cmsPage);

    ResponseResult deleteById(String id);

    String getHtml(String id);

    ResponseResult postPage(String id);
}
