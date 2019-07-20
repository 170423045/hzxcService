package com.hzxc.manage_course.dao;

import com.hzxc.framework.domain.course.Teachplan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by dongf on 2019/7/19.
 */
@Mapper
public interface TeachPlanRepository extends JpaRepository<Teachplan,String>{
       public List<Teachplan> findByCourseidAndAndParentid(String courseId,String parentId);
}
