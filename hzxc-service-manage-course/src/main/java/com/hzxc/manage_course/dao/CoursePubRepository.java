package com.hzxc.manage_course.dao;

import com.hzxc.framework.domain.course.CourseMarket;
import com.hzxc.framework.domain.course.CoursePub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.manage_course.dao
 * @ClassName: CourseMarketRepository
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/23 19:49
 * @Version: 1.0
 */
@Repository
public interface CoursePubRepository extends JpaRepository<CoursePub,String> {
}
