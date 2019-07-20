package com.hzxc.manage_course.controller;

import com.hzxc.api.course.CategoryControllerApi;
import com.hzxc.framework.domain.course.ext.CategoryNode;
import com.hzxc.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongf on 2019/7/20.
 */
@RestController
public class CategoryController implements CategoryControllerApi{
    @Autowired
    private CourseService courseService;
    @Override
    @RequestMapping(value = "/category/list",method = RequestMethod.GET)
    public CategoryNode findList() {
        return courseService.findCategoryList();
    }
}
