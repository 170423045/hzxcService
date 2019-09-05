package com.hzxc.manage_course.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hzxc.framework.domain.cms.CmsPage;
import com.hzxc.framework.domain.cms.response.CmsCode;
import com.hzxc.framework.domain.cms.response.CmsPageResult;
import com.hzxc.framework.domain.course.*;
import com.hzxc.framework.domain.course.ext.CategoryNode;
import com.hzxc.framework.domain.course.ext.CourseInfo;
import com.hzxc.framework.domain.course.ext.CourseView;
import com.hzxc.framework.domain.course.ext.TeachplanNode;
import com.hzxc.framework.domain.course.request.CourseListRequest;
import com.hzxc.framework.domain.course.response.CmsPostPageResult;
import com.hzxc.framework.domain.course.response.CourseCode;
import com.hzxc.framework.domain.course.response.CoursePublishResult;
import com.hzxc.framework.exception.ExceptionCast;
import com.hzxc.framework.model.response.CommonCode;
import com.hzxc.framework.model.response.QueryResponseResult;
import com.hzxc.framework.model.response.QueryResult;
import com.hzxc.framework.model.response.ResponseResult;
import com.hzxc.manage_course.client.CmsPageClient;
import com.hzxc.manage_course.dao.*;
import com.hzxc.manage_course.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by dongf on 2019/7/18.
 */
@Service
public class CourseServiceImpl implements CourseService{
    @Value("${course-publish.siteId}")
    private String  siteId;
    @Value("${course-publish.templateId}")
    private String  templateId;
    @Value("${course-publish.pageWebPath}")
    private String  pageWebPath;
    @Value("${course-publish.pagePhysicaPath}")
    private String  pagePhysicaPath;
    @Value("${course-publish.previewUrl}")
    private String  previewUrl;
    @Value("${course-publish.dataUrlPre}")
    private String  dataUrlPre;

    @Autowired
    private CmsPageClient cmsPageClient;
    @Autowired
    private TeachPlanMapper teachPlanMapper;
    @Autowired
    private TeachPlanRepository teachPlanRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseMarketRepository courseMarketRepository;
    @Autowired
    private CoursePicRepository coursePicRepository;
    @Autowired
    private CoursePubRepository coursePubRepository;


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
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest) {

        if(courseListRequest==null){
            courseListRequest=new CourseListRequest();

        }
        if(page<=0)
            page=1;
        if(size<=0)
            size=10;

        //Page<CourseInfo> course=courseMapper.findCourseList();

        PageHelper.startPage(page,size);
        Page<CourseInfo> courseList = courseMapper.findCourseList(courseListRequest.getCompanyId());

        List<CourseInfo> result = courseList.getResult();
        int total = (int) courseList.getTotal();

        QueryResult<CourseInfo> queryResult = new QueryResult<>();
        queryResult.setList(result);
        queryResult.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    @Override
    public ResponseResult addCourseBase(CourseBase courseBase) {
        if(courseBase==null){
            return new ResponseResult(CommonCode.INVALID_PARAM);
        }
        if(courseBase.getName()==null||courseBase.getMt()==null||courseBase.getSt()==null){
            return new ResponseResult(CommonCode.LACK_PARAM);
        }
        courseBaseRepository.save(courseBase);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    public CourseBase getCourseBaseById(String courseId) {
        if(courseId==null){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            return null;
        }

        return optional.get();
    }

    /**
     * 修改页面信息
     * @param courseId
     * @param courseBase
     * @return
     */
    @Override
    @Transactional
    public ResponseResult updateCourseBase(String courseId, CourseBase courseBase) {

        if(courseBase==null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        if(courseBase.getName()==null||courseBase.getMt()==null||courseBase.getSt()==null){
            ExceptionCast.cast(CommonCode.LACK_PARAM);
        }
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if(!optional.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        CourseBase base = optional.get();

        base.setId(courseId);
        base.setName(courseBase.getName());
        base.setDescription(courseBase.getDescription());
        base.setMt(courseBase.getMt());
        base.setSt(courseBase.getSt());

        base.setUsers(courseBase.getUsers());
        base.setGrade(courseBase.getGrade());
        base.setStudymodel(courseBase.getStudymodel());

        CourseBase save = courseBaseRepository.save(base);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 查询课程营销信息
     * @param courseId
     * @return
     */

    @Override
    @Transactional
    public CourseMarket getCourseMarketById(String courseId) {
        if(courseId==null||StringUtils.isEmpty(courseId)){ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);}
        Optional<CourseMarket> optional = courseMarketRepository.findById(courseId);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 更新课程营销信息
     * @param courseId
     * @param courseMarket
     * @return
     */
    @Override
    public ResponseResult updateCourseMarket(String courseId, CourseMarket courseMarket) {

        Optional<CourseBase> byId1 = courseBaseRepository.findById(courseId);

        if(!byId1.isPresent()){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        if (courseMarket==null){
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }

        Optional<CourseMarket> byId = courseMarketRepository.findById(courseId);
        CourseMarket one=new CourseMarket();
        if (!byId.isPresent()){
            courseMarket.setId(courseId);
            CourseMarket save = courseMarketRepository.save(courseMarket);
            return new ResponseResult(CommonCode.SUCCESS);
        }

        one.setId(courseId);
        one.setCharge(courseMarket.getCharge());
        one.setEndTime(courseMarket.getEndTime());
        one.setPrice(courseMarket.getPrice());
        one.setPriceOld(courseMarket.getPriceOld());
        one.setQq(courseMarket.getQq());
        one.setValid(courseMarket.getValid());
        one.setStartTime(courseMarket.getStartTime());
        courseMarketRepository.save(one);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 保存课程图片信息
     * @param courseId
     * @param pic
     * @return
     */
    @Override
    @Transactional
    public ResponseResult addCoursePic(String courseId, String pic) {
        //在数据库查询课程图片信息
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        CoursePic coursePic=null;
        //有则更新，无则新增
        if (optional.isPresent()){
            coursePic=optional.get();

        }else {
            coursePic=new CoursePic();
            coursePic.setCourseid(courseId);
        }
        coursePic.setPic(pic);

        coursePicRepository.save(coursePic);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @Transactional
    public CoursePic findCoursePicList(String courseId) {
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);
        CoursePic coursePic=null;
        if (optional.isPresent()){
             coursePic = optional.get();
        }
        return coursePic;
    }

    @Override
    @Transactional
    public ResponseResult deleteCoursePic(String courseId) {
        long l = coursePicRepository.deleteByCourseid(courseId);
        if (l>0)
            return new ResponseResult(CommonCode.SUCCESS);
        return new ResponseResult(CommonCode.FAIL);
    }

    /**
     * 获取页面详情的数据模型
     * @param courseId
     * @return
     */
    @Override
    public CourseView getCourseView(String courseId) {
        if(StringUtils.isEmpty(courseId)||courseId==null) return null;
        CourseView courseView=new CourseView();
        //课程基本信息
        Optional<CourseBase> baseOptional = courseBaseRepository.findById(courseId);
        if (baseOptional.isPresent()){
            CourseBase courseBase = baseOptional.get();
            courseView.setCourseBase(courseBase);
        }
        //课程图片信息
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if (picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            courseView.setCoursePic(coursePic);
        }
        //课程营销信息
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(courseId);
        if (marketOptional.isPresent()){
            CourseMarket courseMarket = marketOptional.get();
            courseView.setCourseMarket(courseMarket);
        }
        //课程计划信息
        TeachplanNode teachplanNode = teachPlanMapper.selectList(courseId);
        courseView.setTeachplanNode(teachplanNode);
        return courseView;
    }

    /**
     * 课程详情预览业务
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishResult coursePreview(String courseId) {
        //1.获取该课程的页面信息
        CmsPage cmsPage=getCmsPageByCourseId(courseId);
        //2.远程调用CMS更新页面服务，将该课程详情页面保存至数据库，并得到该页面id
        CmsPageResult cmsPageResult = cmsPageClient.save(cmsPage);
        //3.获取页面预览url：previewUrl+pageId
        String preview=previewUrl+cmsPageResult.getCmsPage().getPageId();
        //4.返回CoursePublishResult（携带预览该页面的url接口路径）
        return new CoursePublishResult(CommonCode.SUCCESS,preview);
    }

    /**
     * 封装课程的页面信息
     * @param courseId
     * @return
     */
    private CmsPage getCmsPageByCourseId(String courseId) {
        Optional<CourseBase> optional = courseBaseRepository.findById(courseId);
        if (!optional.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_COURSEIDISNULL);
        }
        CourseBase courseBase = optional.get();
        //1.添加课程对应的页面信息
        CmsPage cmsPage=new CmsPage();
        //1.封装课程详情页面信息
        cmsPage.setDataUrl(dataUrlPre+courseId);
        cmsPage.setPageAliase(courseBase.getName());
        cmsPage.setPageName(courseId+".html");
        cmsPage.setSiteId(siteId);
        cmsPage.setPagePhysicalPath(pagePhysicaPath);
        cmsPage.setPageWebPath(pageWebPath);
        cmsPage.setTemplateId(templateId);
        return cmsPage;
    }

    //一键发布
    @Override
    @Transactional
    public CoursePublishResult publish(String courseId) {
        //1.封装该课程的页面信息
        CmsPage cmsPage = getCmsPageByCourseId(courseId);
        //2.远程调用CMS一键发布服务
        CmsPostPageResult cmsPostPageResult = cmsPageClient.postPageQuick(cmsPage);
        if (!cmsPostPageResult.isSuccess()){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_SAVEHTMLERROR);
        }
        //3.获取页面访问路径
        String pageUrl = cmsPostPageResult.getPageUrl();
        //4.更新课程状态
        CourseBase courseBase = getCourseBaseById(courseId);
        courseBase.setStatus("202002");
        updateCourseBase(courseId,courseBase);
        //5.建立索引信息
        //5.1创建CoursePub
        CoursePub coursePub=createCoursePub(courseId);
        //5.2将CoursePub保存进数据库
        saveCoursePub(courseId,coursePub);

        return new CoursePublishResult(CommonCode.SUCCESS,pageUrl);
    }

    private CoursePub saveCoursePub(String courseId, CoursePub coursePub) {
        //1.通过id进行查询
        Optional<CoursePub> pubOptional = coursePubRepository.findById(courseId);
        CoursePub one=null;
        //2.有则更新，无则新建
        if (pubOptional.isPresent()){
            one=pubOptional.get();
        }else {
            one=new CoursePub();
        }
        //3.cope当前发布页面的CoursePub对象
        BeanUtils.copyProperties(coursePub,one);
        //3.1确保携有课程ID
        one.setId(courseId);
        //3.2创建时间戳与发布时间
        one.setTimestamp(new Date());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        one.setPubTime(format);
        return coursePubRepository.save(one);

    }

    //创建CoursePub
    private CoursePub createCoursePub(String courseId) {
        CoursePub coursePub=new CoursePub();
        //封装CourseBase
        Optional<CourseBase> courseBaseOptional = courseBaseRepository.findById(courseId);
        if (!courseBaseOptional.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
        }
        CourseBase courseBase = courseBaseOptional.get();
        BeanUtils.copyProperties(courseBase,coursePub);
        //封装CourseMarket
        Optional<CourseMarket> marketOptional = courseMarketRepository.findById(courseId);
        if (!marketOptional.isPresent()){
            ExceptionCast.cast(CourseCode.COURSE_PUBLISH_VIEWERROR);
        }
        CourseMarket courseMarket=marketOptional.get();
        BeanUtils.copyProperties(courseMarket,coursePub);
        //封装课程图片
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        if (picOptional.isPresent()){
            CoursePic coursePic = picOptional.get();
            BeanUtils.copyProperties(coursePic,coursePub);
        }
        //封装teachplan
        TeachplanNode teachplanNode = teachPlanMapper.selectList(courseId);
        String jsonString = JSON.toJSONString(teachplanNode);
        coursePub.setTeachplan(jsonString);
        return coursePub;
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
}
