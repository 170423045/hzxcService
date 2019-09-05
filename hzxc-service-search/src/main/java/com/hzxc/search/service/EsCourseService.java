package com.hzxc.search.service;

import com.hzxc.framework.domain.course.CoursePub;
import com.hzxc.framework.domain.search.CourseSearchParam;
import com.hzxc.framework.model.response.QueryResponseResult;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.search.service
 * @ClassName: EsCourseService
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/30 17:56
 * @Version: 1.0
 */
@Service
public interface EsCourseService {
    QueryResponseResult<CoursePub> list(Integer page, Integer size, CourseSearchParam courseSearchParam);
}
