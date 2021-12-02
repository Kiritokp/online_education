package com.xiaobai.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaobai.eduservice.entity.EduCourse;
import com.xiaobai.eduservice.entity.frontvo.CourseWebVo;
import com.xiaobai.eduservice.entity.vo.CoursePublishVo;
import com.xiaobai.eduservice.entity.vo.CourseWebVoOrder;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishVo(String courseId);
    CourseWebVo selectInfoWebById(String id);
    CourseWebVoOrder getCourseOrderById(String id);
}
