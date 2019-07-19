package com.hzxc.manage_course.controller;

import com.hzxc.api.course.CourseControllerApi;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
