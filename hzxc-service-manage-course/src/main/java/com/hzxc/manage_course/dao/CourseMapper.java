package com.hzxc.manage_course.dao;

import com.github.pagehelper.Page;
import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.ext.CourseInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   CourseBase findCourseBaseById(String id);
   //查询出课程列表信息
   Page<CourseInfo> findCourseList();
   //聚合函数求出数据总数
   Integer findCounts();
}
