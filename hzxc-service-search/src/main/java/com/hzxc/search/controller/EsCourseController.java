package com.hzxc.search.controller;

import com.hzxc.api.search.EsCourseControllerApi;
import com.hzxc.framework.domain.course.CoursePub;
import com.hzxc.framework.domain.search.CourseSearchParam;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.search.service.EsCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.search.controller
 * @ClassName: EsCourseController
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/30 17:54
        * @Version: 1.0
        */
@RestController
@RequestMapping("/search/course")
public class EsCourseController implements EsCourseControllerApi {
    @Autowired
    EsCourseService esCourseService;
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult<CoursePub> list(@PathVariable("page") Integer page,
                                               @PathVariable("size") Integer size,
                                               CourseSearchParam courseSearchParam) {
        return esCourseService.list(page,size,courseSearchParam);
    }
}
