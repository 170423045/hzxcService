package com.hzxc.manage_course.service.impl;

import com.hzxc.framework.domain.course.ext.CategoryNode;
import com.hzxc.manage_course.dao.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: hzxcService
 * @Package: com.hzxc.manage_course.service.impl
 * @ClassName: CategoryServiceImpl
 * @Author: Pulia
 * @Description: ${description}
 * @Date: 2019/7/22 22:13
 * @Version: 1.0
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    public CategoryNode findCategoryList() {
        return categoryMapper.findCategoryList();
    }
}
