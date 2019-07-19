package com.hzxc.manage_course;

import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.manage_course.dao.CourseBaseRepository;
import com.hzxc.manage_course.dao.CourseMapper;
import com.hzxc.manage_course.dao.TeachPlanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * Created by dongf on 2019/7/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeachPlanMapper teachPlanMapper;
    @Test
    public void testCourseBaseRepository(){
        Optional<CourseBase> optional = courseBaseRepository.findById("402885816240d276016240f7e5000002");
        if(optional.isPresent()){
            CourseBase courseBase = optional.get();
            System.out.println(courseBase);
        }

    }

    @Test
    public void testCourseMapper(){
        CourseBase courseBase = courseMapper.findCourseBaseById("402885816240d276016240f7e5000002");
        System.out.println(courseBase);

    }
    @Test
    public void test1(){
        TeachplanNode teachplanNode = teachPlanMapper.selectList("4028e58161bcf7f40161bcf8b77c0000");
        System.out.println(teachplanNode);
    }
}

