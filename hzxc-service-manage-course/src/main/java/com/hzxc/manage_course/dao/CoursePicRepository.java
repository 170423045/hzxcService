package com.hzxc.manage_course.dao;

import com.hzxc.framework.domain.course.CoursePic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.manage_course.dao
 * @ClassName: CoursePicRepository
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/26 10:49
 * @Version: 1.0
 */
@Repository
public interface CoursePicRepository extends JpaRepository<CoursePic,String> {
    long deleteByCourseid(String courseId);
}
