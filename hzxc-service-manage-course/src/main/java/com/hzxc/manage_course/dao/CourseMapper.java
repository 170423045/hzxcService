package com.hzxc.manage_course.dao;

import com.hzxc.framework.domain.course.CourseBase;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   CourseBase findCourseBaseById(String id);
}
