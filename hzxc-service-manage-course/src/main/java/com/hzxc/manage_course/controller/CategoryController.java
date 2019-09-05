package com.hzxc.manage_course.controller;

import com.hzxc.api.course.CategoryControllerApi;
import com.hzxc.framework.domain.course.ext.CategoryNode;
import com.hzxc.manage_course.service.CourseService;
import com.hzxc.manage_course.service.impl.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dongf on 2019/7/20.
 */
@RestController
@RequestMapping("/category")
public class CategoryController implements CategoryControllerApi{
    @Autowired
    private CategoryService categoryService;
    @Override
    @GetMapping(value = "/list")
    public CategoryNode findList() {
        return categoryService.findCategoryList();
    }
}
