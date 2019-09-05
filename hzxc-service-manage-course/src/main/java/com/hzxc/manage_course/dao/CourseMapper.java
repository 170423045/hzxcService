package com.hzxc.manage_course.dao;

import com.github.pagehelper.Page;
import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.ext.CourseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator.
 */
@Repository
@Mapper
public interface CourseMapper {
   CourseBase findCourseBaseById(String id);
   //查询出课程列表信息
   Page<CourseInfo> findCourseList(@Param("companyId") String companyId);

}
