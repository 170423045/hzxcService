package com.hzxc.manage_course.controller;

import com.hzxc.api.course.CourseControllerApi;
import com.hzxc.framework.domain.course.Teachplan;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dongf on 2019/7/18.
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi{
    @Autowired
    private CourseService courseService;
    @Override
    @RequestMapping(value = "/teachplan/list/{courseId}",method = RequestMethod.GET)
    public TeachplanNode findTeachplanList(@PathVariable(value = "courseId") String courseId) {
        return courseService.findTeachplanList(courseId);
    }

    @Override
    @RequestMapping(value = "/teachplan/add",method = RequestMethod.POST)
    public ResponseResult addTeachPlan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachPlan(teachplan);
    }

    @Override
    @RequestMapping(value = "/coursebase/list/{page}/{size}",method = RequestMethod.GET)
    public QueryResponseResult findCourseList(@PathVariable(value = "page") int page, @PathVariable(value = "size") int size) {
         //分页查询后台的课程列表数据
        return courseService.findCourseList(page,size);
    }
}
