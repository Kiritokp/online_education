package com.xiaobai.eduservice.service;

import com.xiaobai.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-20
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();

    List<EduSubject> findAllOneSubject();

    List<EduSubject> findTwoSubject(String id);
}
