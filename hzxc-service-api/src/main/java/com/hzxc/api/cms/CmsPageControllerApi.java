package com.hzxc.api.cms;

import com.hzxc.framework.domain.cms.CmsPage;
import com.hzxc.framework.domain.cms.request.QueryPageRequest;
import com.hzxc.framework.domain.cms.response.CmsPageResult;
import com.hzxc.framework.domain.course.response.CmsPostPageResult;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api(value = "cms页面管理接口",description = "cms页面管理接口，提供增删查改")
public interface CmsPageControllerApi {
    /**
     *
     * @param page  当前页
     * @param size  每页长度
     * @param queryPageRequest 查询条件
     * @return
     */
    @ApiOperation("分页列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页码",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name = "size",value = "每页记录数",required = true,paramType = "path",dataType = "int"),
            @ApiImplicitParam(name = "queryPageRequest",value = "查询条件",required = false,paramType = "path",dataType = "QueryPageRequest")
    })
    QueryResponseResult findPageList(Integer page, Integer size, QueryPageRequest queryPageRequest);


    @ApiOperation("站点选项列表")
    QueryResponseResult findSiteList();

    @ApiOperation("新增页面信息")
    CmsPageResult add(CmsPage cmsPage);

    @ApiOperation("根据id查询页面")
    CmsPage findById(String id);

    @ApiOperation("修改页面")
    CmsPageResult edit(String id,CmsPage cmsPage);

    @ApiOperation("根据id删除页面")
    ResponseResult deleteById(String id);

    @ApiOperation("发布页面")
    ResponseResult postPage(String id);

    @ApiOperation("更新页面")
    public CmsPageResult save(CmsPage cmsPage);

    @ApiOperation("一键发布课程详情页面")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
