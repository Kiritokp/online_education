package com.xiaobai.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.eduservice.entity.EduChapter;
import com.xiaobai.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
public interface EduChapterService extends IService<EduChapter> {


    List<ChapterVo> geChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
