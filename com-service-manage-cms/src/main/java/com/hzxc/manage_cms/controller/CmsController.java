package com.hzxc.manage_cms.controller;

import com.hzxc.api.cms.CmsPageControllerApi;
import com.hzxc.framework.domain.cms.CmsPage;
import com.hzxc.framework.domain.cms.request.QueryPageRequest;
import com.hzxc.framework.domain.cms.response.CmsPageResult;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.manage_cms.service.CmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/cms/page")
public class CmsController implements CmsPageControllerApi {

   @Autowired
    CmsService cmsService;

    /**
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    //分页查询cms列表
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findPageList(@PathVariable("page") Integer page, @PathVariable("size")Integer size, QueryPageRequest queryPageRequest) {
        /*QueryResult<CmsPage> queryResult=new QueryResult<>();
        List<CmsPage> list=new ArrayList<>();
        CmsPage cmsPage=new CmsPage();
        cmsPage.setPageName("测试页面");
        list.add(cmsPage);
        queryResult.setList(list);
        QueryResponseResult queryResponseResult=new QueryResponseResult(CommonCode.SUCCESS,queryResult);
        System.out.println(queryResponseResult);*/

        return cmsService.findPageList(page,size,queryPageRequest);
    }

    //获取站点列表
    @Override
    @GetMapping("/siteList")
    public QueryResponseResult findSiteList() {
        return cmsService.findSiteList();
    }

    //添加页面信息
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {

        return cmsService.add(cmsPage);
    }

    //通过id查询页面
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable String id) {
        return cmsService.findById(id);
    }

    //更改页面信息
    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable String id, @RequestBody CmsPage cmsPage) {
        return cmsService.edit(id,cmsPage);
    }

    //删除页面
    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteById(@PathVariable String id) {
        return cmsService.deleteById(id);
    }

    //发布页面信息
    @Override
    @PostMapping("postPage/{pageId}")
    public ResponseResult postPage(@PathVariable("pageId") String pageId) {
        return cmsService.postPage(pageId);
    }


}