package com.hzxc.manage_course.service;

import com.hzxc.framework.domain.course.ext.TeachplanNode;

/**
 * Created by dongf on 2019/7/18.
 */
public interface CourseService {
    public TeachplanNode findTeachplanList(String courseId);
}
