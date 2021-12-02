package com.xiaobai.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-05-23
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> selectPopularTeacher();

    Map<String, Object> selectTeacherPage(Page<EduTeacher> teacherPage);
}
