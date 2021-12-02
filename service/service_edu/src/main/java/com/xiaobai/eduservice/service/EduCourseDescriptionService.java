package com.xiaobai.eduservice.service;

import com.xiaobai.eduservice.entity.EduCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
public interface EduCourseDescriptionService extends IService<EduCourseDescription> {

    void removeCourseDescriptionByCourseId(String courseId);
}
