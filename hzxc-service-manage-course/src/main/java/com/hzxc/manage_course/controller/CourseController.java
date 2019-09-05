package com.hzxc.manage_course.controller;

import com.hzxc.api.course.CourseControllerApi;
import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.CourseMarket;
import com.hzxc.framework.domain.course.CoursePic;
import com.hzxc.framework.domain.course.Teachplan;
import com.hzxc.framework.domain.course.ext.CourseView;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.domain.course.request.CourseListRequest;
import com.hzxc.framework.domain.course.response.CoursePublishResult;
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
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachPlan(@RequestBody Teachplan teachplan) {
        return courseService.addTeachPlan(teachplan);
    }



    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable(value = "page") int page,
                                              @PathVariable(value = "size") int size,
                                              CourseListRequest courseListRequest) {
         //分页查询后台的课程列表数据
        return courseService.findCourseList(page,size,courseListRequest);
    }

    @Override
    @PostMapping("/coursebase/add")
    public ResponseResult addCourseBase(@RequestBody CourseBase courseBase) {
        return courseService.addCourseBase(courseBase);
    }

    @Override
    @GetMapping("/findcourse/{courseId}")
    public CourseBase getCourseBaseById(@PathVariable("courseId") String courseId) {
        return courseService.getCourseBaseById(courseId);
    }

    @Override
    @PutMapping("/update/{courseId}")
    public ResponseResult updateCourseBase(@PathVariable("courseId") String courseId,
                                           @RequestBody CourseBase courseBase) {
        return courseService.updateCourseBase(courseId,courseBase);
    }

    @Override
    @GetMapping("/coursemarket/get/{courseId}")
    public CourseMarket getCourseMarketById(@PathVariable("courseId") String courseId) {

        return courseService.getCourseMarketById(courseId);
    }

    @Override
    @PutMapping("/coursemarket/update/{courseId}")
    public ResponseResult updateCourseMarket(@PathVariable("courseId") String courseId,@RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(courseId,courseMarket);
    }

    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId,
                                       @RequestParam("pic") String pic) {
        return courseService.addCoursePic(courseId,pic);
    }



    @Override
    @DeleteMapping("/coursepic/delete")
    public ResponseResult deleteCoursePic(@RequestParam("courseId") String courseId) {
        return courseService.deleteCoursePic(courseId);
    }

    @Override
    @GetMapping("/courseview/{courseId}")
    public CourseView getCourseView(@PathVariable("courseId") String courseId) {
        return courseService.getCourseView(courseId);
    }

    @Override
    @PostMapping("/preview/{id}")
    public CoursePublishResult coursePreview(@PathVariable("id") String id) {
        return courseService.coursePreview(id);
    }

    /**
     * 一键发布
     * @param courseId
     * @return
     */
    @Override
    @PostMapping("/publish/{courseId}")
    public CoursePublishResult publish(@PathVariable("courseId") String courseId) {
        return courseService.publish(courseId);
    }

    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePicList(@PathVariable("courseId") String courseId) {

        return courseService.findCoursePicList(courseId);
    }

}
