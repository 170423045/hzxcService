package com.hzxc.api.search;

import com.hzxc.framework.domain.course.CoursePub;
import com.hzxc.framework.domain.search.CourseSearchParam;
import com.hzxc.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.api.search
 * @ClassName: EsCourseControllerApi
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/30 17:27
 * @Version: 1.0
 */
@Api(value = "搜索服务",description = "提供课程搜索服务",tags = {"搜索系统"})
public interface EsCourseControllerApi {
    @ApiOperation("课程综合搜索")
    public QueryResponseResult<CoursePub> list(Integer page, Integer size, CourseSearchParam courseSearchParam);
}
