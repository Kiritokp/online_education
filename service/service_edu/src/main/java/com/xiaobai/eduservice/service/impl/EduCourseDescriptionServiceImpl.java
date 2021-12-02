package com.xiaobai.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.eduservice.entity.EduCourseDescription;
import com.xiaobai.eduservice.mapper.EduCourseDescriptionMapper;
import com.xiaobai.eduservice.service.EduCourseDescriptionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    //根据课程id删除课程描述
    @Override
    public void removeCourseDescriptionByCourseId(String courseId) {
        QueryWrapper<EduCourseDescription> eduCourseDescriptionQueryWrapper = new QueryWrapper<>();
        eduCourseDescriptionQueryWrapper.eq("id",courseId);
        baseMapper.delete(eduCourseDescriptionQueryWrapper);
    }
}
