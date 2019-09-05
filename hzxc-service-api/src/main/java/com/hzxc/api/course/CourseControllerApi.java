package com.hzxc.api.course;

import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.CourseMarket;
import com.hzxc.framework.domain.course.CoursePic;
import com.hzxc.framework.domain.course.Teachplan;
import com.hzxc.framework.domain.course.ext.CourseInfo;
import com.hzxc.framework.domain.course.ext.CourseView;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.domain.course.request.CourseListRequest;
import com.hzxc.framework.domain.course.response.CoursePublishResult;
import com.hzxc.framework.domain.system.SysDictionary;
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

    @ApiOperation("我的课程")
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("添加课程")
    ResponseResult addCourseBase(CourseBase courseBase);

    @ApiOperation("查询课程基本信息")
    CourseBase getCourseBaseById(String courseId);

    @ApiOperation("修改课程基本信息")
    ResponseResult updateCourseBase(String courseId,CourseBase courseBase);

    @ApiOperation("查询销售信息")
    CourseMarket getCourseMarketById(String courseId);

    @ApiOperation("更新销售信息")
    ResponseResult updateCourseMarket(String courseId,CourseMarket courseMarket);

    @ApiOperation("保存课程图片信息")
    ResponseResult addCoursePic(String courseId,String pic);

    @ApiOperation("查询课程图片信息")
    CoursePic findCoursePicList(String courseId);

    @ApiOperation("删除课程图片信息")
    ResponseResult deleteCoursePic(String courseId);

    @ApiOperation("查询课程详细信息")
    CourseView getCourseView(String courseId);

    @ApiOperation("获取预览课程详情URL")
    CoursePublishResult coursePreview(String id);

    @ApiOperation("一键发布课程详情页面")
    CoursePublishResult publish(String id);
}
