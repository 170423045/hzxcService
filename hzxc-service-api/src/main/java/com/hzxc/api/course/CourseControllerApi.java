package com.hzxc.api.course;

import com.hzxc.framework.domain.course.Teachplan;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by dongf on 2019/7/17.
 */
@Api(value = "课程管理接口",description = "课程管理接口，提供课程的接口，提供增删查改")
public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    public TeachplanNode findTeachplanList(String courseId);
    //添加课程计划
    @ApiOperation("添加课程计划")
    public ResponseResult addTeachPlan(Teachplan teachplan);
    @ApiOperation("课程分页查询")
    public QueryResponseResult findCourseList(int page, int size);
}
