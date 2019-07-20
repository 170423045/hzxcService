package com.hzxc.manage_course.service;

import com.hzxc.framework.domain.course.Teachplan;
import com.hzxc.framework.domain.course.ext.CategoryNode;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.ResponseResult;

/**
 * Created by dongf on 2019/7/18.
 */
public interface CourseService {
    public TeachplanNode findTeachplanList(String courseId);

    ResponseResult addTeachPlan(Teachplan teachplan);
    //分页查询出所有的课程信息
    QueryResponseResult findCourseList(int page, int size);
    //查询出课程分类树
    public CategoryNode findCategoryList();
}
