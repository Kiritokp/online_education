package com.xiaobai.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.eduservice.entity.EduCourse;
import com.xiaobai.eduservice.entity.EduCourseDescription;
import com.xiaobai.eduservice.entity.frontvo.CourseQueryVo;
import com.xiaobai.eduservice.entity.frontvo.CourseWebVo;
import com.xiaobai.eduservice.entity.vo.CourseInfoVo;
import com.xiaobai.eduservice.entity.vo.CoursePublishVo;
import com.xiaobai.eduservice.entity.vo.CourseWebVoOrder;
import com.xiaobai.eduservice.mapper.EduCourseMapper;
import com.xiaobai.eduservice.service.EduChapterService;
import com.xiaobai.eduservice.service.EduCourseDescriptionService;
import com.xiaobai.eduservice.service.EduCourseService;
import com.xiaobai.eduservice.service.EduVideoService;
import com.xiaobai.servicebase.exceptionhandle.CustomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    //课程章节注入
    @Autowired
    private EduChapterService eduChapterService;
    //课程小节注入
    @Autowired
    private EduVideoService eduVideoService;

    //添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert<=0){
            throw new CustomException(20001,"添加课程失败");
        }
        //2 向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescription.setId(eduCourse.getId());
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if (!save){
            throw new CustomException(20001,"添加课程简介失败");
        }
        return eduCourse.getId();
    }

    //根据课程id获得课程信息
    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        // 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //查询描述表
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        if (eduCourseDescription==null){
            throw new CustomException(20001,"请输入正确的课程ID");
        }
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        baseMapper.updateById(eduCourse);
        //2 修改课程简介表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        boolean flag = eduCourseDescriptionService.updateById(eduCourseDescription);
        if (!flag){
            throw new CustomException(20001,"修改课程简介失败");
        }
    }

    //得到课程的确认发布信息
    @Override
    public CoursePublishVo getCoursePublishVo(String courseId) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishVo(courseId);
        return coursePublishVo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除课程小节
        eduVideoService.removeVideoByCourseId(courseId);

        //根据课程id删除课程章节
        eduChapterService.removeChapterByCourseId(courseId);

        //根据课程id删除课程描述
        eduCourseDescriptionService.removeCourseDescriptionByCourseId(courseId);

        //根据课程id删除课程
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.eq("id",courseId);
        int delete = baseMapper.delete(eduCourseQueryWrapper);
        if (delete==0){
            throw new CustomException(20001,"删除失败！");
        }
    }

    //获取8个最热门课程
    @Cacheable(key = "'selectCourseList'",value = "banner")
    @Override
    public List<EduCourse> selectPopularCourse() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");

        List<EduCourse> courseList = baseMapper.selectList(wrapper);
        return courseList;
    }

    //课程条件查询带分页
    @Override
    public Map<String, Object> selectCoursePage(Page<EduCourse> eduCoursePage, CourseQueryVo courseQueryVo) {
        //条件查询
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) {  //一级分类
            queryWrapper.eq("subject_parent_id", courseQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())) { //二级分类
            queryWrapper.eq("subject_id", courseQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {  //购买量
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) { //创建时间
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())) { // 价格
            queryWrapper.orderByDesc("price");
        }

        //分页
        baseMapper.selectPage(eduCoursePage,queryWrapper);

        List<EduCourse> records = eduCoursePage.getRecords();
        long current = eduCoursePage.getCurrent();
        long pages = eduCoursePage.getPages();
        long size = eduCoursePage.getSize();
        long total = eduCoursePage.getTotal();
        boolean hasNext = eduCoursePage.hasNext();
        boolean hasPrevious = eduCoursePage.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    //根据课程id查询信息
    @Override
    public CourseWebVo selectInfoWebById(String id) {
        return baseMapper.selectInfoWebById(id);
    }

    @Override
    public CourseWebVoOrder getCourseOrderById(String id) {
        return baseMapper.getCourseOrderById(id);
    }
}
