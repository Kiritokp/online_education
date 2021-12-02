package com.xiaobai.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.eduservice.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-17
 */
public interface EduCommentService extends IService<EduComment> {

    Map<String, Object> selectCommentPage(Page<EduComment> eduCommentPage, String courseId);
}
