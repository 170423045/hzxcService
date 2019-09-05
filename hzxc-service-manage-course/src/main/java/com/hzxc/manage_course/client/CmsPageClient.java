package com.hzxc.manage_course.client;

import com.hzxc.framework.domain.cms.CmsPage;
import com.hzxc.framework.domain.cms.response.CmsPageResult;
import com.hzxc.framework.domain.course.response.CmsPostPageResult;
import com.hzxc.framework.model.response.QueryResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.manage_course.client
 * @ClassName: CmsPageClient
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/27 11:03
 * @Version: 1.0
 */
@FeignClient("HZXC-SERVICE-MANAGER-CMS")
public interface CmsPageClient {
    @GetMapping("/cms/page/siteList")
    public QueryResponseResult findSiteList();

    @PostMapping("/cms/page/save")
    public CmsPageResult save(@RequestBody CmsPage cmsPage);

    @PostMapping("/cms/page/postPageQuick")
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage);

}
