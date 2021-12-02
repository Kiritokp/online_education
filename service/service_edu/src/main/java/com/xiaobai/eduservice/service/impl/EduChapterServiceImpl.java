package com.xiaobai.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.eduservice.entity.EduChapter;
import com.xiaobai.eduservice.entity.EduVideo;
import com.xiaobai.eduservice.entity.chapter.ChapterVo;
import com.xiaobai.eduservice.entity.chapter.VideoVo;
import com.xiaobai.eduservice.mapper.EduChapterMapper;
import com.xiaobai.eduservice.service.EduChapterService;
import com.xiaobai.eduservice.service.EduVideoService;
import com.xiaobai.servicebase.exceptionhandle.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    
    @Autowired
    private EduVideoService eduVideoService;

    //根据课程id查出所有的章节和小结
    @Override
    public List<ChapterVo> geChapterVideoByCourseId(String courseId) {

        // 根据课程id查出所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);

        List<EduChapter> eduChapters = baseMapper.selectList(wrapperChapter);

        List<ChapterVo> finalChapters = new ArrayList<>();

        for (int i = 0; i <eduChapters.size() ; i++) {
            EduChapter eduChapter = eduChapters.get(i);
            // 封装所有的章节
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            // 根据章节id查出所有的小结
            QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
            wrapperVideo.eq("chapter_id",eduChapter.getId());
            List<EduVideo> eduVideos = eduVideoService.list(wrapperVideo);

            List<VideoVo> finalVideos = new ArrayList<>();
            for (int j = 0; j < eduVideos.size(); j++) {
                // 封装所有的小结
                EduVideo eduVideo = eduVideos.get(j);
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo,videoVo);
                finalVideos.add(videoVo);
            }
            chapterVo.setChildren(finalVideos);
            finalChapters.add(chapterVo);
        }
        return finalChapters;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        if (count>0){
            //如果章节下面有小节则不能删除
            throw new CustomException(20001,"不能删除");
        }
        //如果章节下面没有小结则可以删除
        int result = baseMapper.deleteById(chapterId);
        return result>0;
    }

    //根据课程id删除课程章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> eduChapterQueryWrapper = new QueryWrapper<>();
        eduChapterQueryWrapper.eq("course_id",courseId);
        baseMapper.delete(eduChapterQueryWrapper);
    }
}
