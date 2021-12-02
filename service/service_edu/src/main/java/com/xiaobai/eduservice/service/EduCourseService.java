package com.xiaobai.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.eduservice.entity.EduCourse;
import com.xiaobai.eduservice.entity.frontvo.CourseQueryVo;
import com.xiaobai.eduservice.entity.frontvo.CourseWebVo;
import com.xiaobai.eduservice.entity.vo.CourseInfoVo;
import com.xiaobai.eduservice.entity.vo.CoursePublishVo;
import com.xiaobai.eduservice.entity.vo.CourseWebVoOrder;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishVo(String CourseId);

    void removeCourse(String courseId);

    List<EduCourse> selectPopularCourse();

    Map<String, Object> selectCoursePage(Page<EduCourse> eduCoursePage, CourseQueryVo courseQueryVo);

    CourseWebVo selectInfoWebById(String id);

    CourseWebVoOrder getCourseOrderById(String id);
}
