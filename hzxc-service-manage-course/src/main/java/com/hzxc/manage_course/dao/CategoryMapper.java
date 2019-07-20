package com.hzxc.manage_course.dao;

import com.hzxc.framework.domain.course.ext.CategoryNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by dongf on 2019/7/20.
 */
@Mapper
public interface CategoryMapper {
    //查询出课程分类信息
    public CategoryNode findCategoryList();
}
