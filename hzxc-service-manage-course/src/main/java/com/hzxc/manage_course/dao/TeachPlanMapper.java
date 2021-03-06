package com.hzxc.manage_course.dao;

import com.hzxc.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by dongf on 2019/7/18.
 */
@Mapper
@Repository
public interface TeachPlanMapper {
    //课程计划查询
    public TeachplanNode selectList(@Param("courseId") String courseId);
}
