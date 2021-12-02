package com.xiaobai.eduservice.service;

import com.xiaobai.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);

    String getVideoSourceId(String videoId);
}
