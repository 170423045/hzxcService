package com.hzxc.manage_course.service.impl;

import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.manage_course.dao.TeachPlanMapper;
import com.hzxc.manage_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dongf on 2019/7/18.
 */
@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private TeachPlanMapper teachPlanMapper;

    @Override
    public TeachplanNode findTeachplanList(String courseId) {
        return teachPlanMapper.selectList(courseId);
    }
}
