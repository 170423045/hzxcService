package com.hzxc.manage_course.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hzxc.framework.domain.course.CourseBase;
import com.hzxc.framework.domain.course.Teachplan;
import com.hzxc.framework.domain.course.ext.CategoryNode;
import com.hzxc.framework.domain.course.ext.CourseInfo;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.exception.ExceptionCast;
import com.hzxc.framework.model.response.CommonCode;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.QueryResult;
import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.manage_course.dao.*;
import com.hzxc.manage_course.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by dongf on 2019/7/18.
 */
@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    private TeachPlanMapper teachPlanMapper;
    @Autowired
    private TeachPlanRepository teachPlanRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public TeachplanNode findTeachplanList(String courseId) {
        return teachPlanMapper.selectList(courseId);
    }
       //添加课程计划
    @Transactional
    @Override
    public ResponseResult addTeachPlan(Teachplan teachplan) {
        if (teachplan==null|| StringUtils.isEmpty(teachplan.getCourseid())||StringUtils.isEmpty(teachplan.getPname())){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        //处理parentid
        String courseid = teachplan.getCourseid();
        String parentid = teachplan.getParentid();
        //如果父级id是空的话怎么做，证明这是一门新添的课程一级节点
        if (StringUtils.isEmpty(parentid)){
          //取出该课程的根节点
            parentid  = this.getTeachplanRoot(courseid);
        }
        //查询出有父级id
        Teachplan teachPlanNew = new Teachplan();
        Teachplan parentNode = teachPlanRepository.findById(parentid).get();
        //将页面提交的teachplan信息提交的teachplanNew中
        BeanUtils.copyProperties(teachplan,teachPlanNew);
        teachPlanNew.setParentid(parentid);
        teachPlanNew.setCourseid(courseid);
        if(parentNode.getGrade().equals("1")) {
            teachPlanNew.setGrade("2");
        }else {
            teachPlanNew.setGrade("3");
        }
        teachPlanRepository.save(teachPlanNew);
        return new ResponseResult(CommonCode.SUCCESS);
    }
   //查询课程列表信息
    @Override
    public QueryResponseResult findCourseList(int page, int size) {
        //Page<CourseInfo> course=courseMapper.findCourseList();
        Integer counts = courseMapper.findCounts();
        PageHelper.startPage(page,size);
        Page<CourseInfo> courseList = courseMapper.findCourseList();
        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setList(courseList);
        queryResult.setTotal(counts);
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    @Override
    public CategoryNode findCategoryList() {
        return categoryMapper.findCategoryList();
    }

    //查询课程的根节点，如果查询不到自动添加根节点
    private String getTeachplanRoot(String courseId){
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()){
            return null;
        }
        //获取课程名
        CourseBase courseBase = optional.get();
        String name = courseBase.getName();
        List<Teachplan> teachplanList = teachPlanRepository.findByCourseidAndAndParentid(courseId, "0");
        if (teachplanList==null||teachplanList.size()<=0){
            //查询不到，添加根节点
            Teachplan teachplan = new Teachplan();
            teachplan.setGrade("1");
            teachplan.setParentid("0");
            teachplan.setPname(name);
            teachplan.setCourseid(courseId);
            teachplan.setStatus("0");
            teachPlanRepository.save(teachplan);
            return teachplan.getId();
        }
        return teachplanList.get(0).getId();
    }
    //分页查询后台的课程数据

}
