package com.hzxc.manage_course.service;

import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.CourseMarket;
import com.hzxc.framework.domain.course.CoursePic;
import com.hzxc.framework.domain.course.Teachplan;
import com.hzxc.framework.domain.course.ext.CategoryNode;
import com.hzxc.framework.domain.course.ext.CourseView;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.domain.course.request.CourseListRequest;
import com.hzxc.framework.domain.course.response.CoursePublishResult;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.ResponseResult;

/**
 * Created by dongf on 2019/7/18.
 */
public interface CourseService {
    public TeachplanNode findTeachplanList(String courseId);

    ResponseResult addTeachPlan(Teachplan teachplan);
    //分页查询出所有的课程信息
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    //新增课程
    ResponseResult addCourseBase(CourseBase courseBase);

    //查询课程基本信息
    CourseBase getCourseBaseById(String courseId);

    //修改课程信息
    ResponseResult updateCourseBase(String courseId, CourseBase courseBase);

    //查询课程营销信息
    CourseMarket getCourseMarketById(String courseId);

    //更新课程营销信息
    ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket);

    ResponseResult addCoursePic(String courseId, String pic);

    CoursePic findCoursePicList(String courseId);

    ResponseResult deleteCoursePic(String courseId);

    CourseView getCourseView(String courseId);

    CoursePublishResult coursePreview(String courseId);

    CoursePublishResult publish(String courseId);
}
