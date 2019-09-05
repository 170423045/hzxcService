package com.hzxc.manage_course;

import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.ext.CategoryNode;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.manage_course.client.CmsPageClient;
import com.hzxc.manage_course.dao.CategoryMapper;
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
public class TestClient {
    @Autowired
    CmsPageClient cmsPageClient;
    @Test
    public void testfeign(){
        QueryResponseResult siteList = cmsPageClient.findSiteList();

        System.out.println(siteList);

    }


}

