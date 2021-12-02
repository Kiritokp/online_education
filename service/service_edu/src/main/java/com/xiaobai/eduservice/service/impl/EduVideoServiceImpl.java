package com.xiaobai.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.eduservice.client.VodClient;
import com.xiaobai.eduservice.entity.EduVideo;
import com.xiaobai.eduservice.mapper.EduVideoMapper;
import com.xiaobai.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //根据课程id删除课程小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据课程id查询所有的视频id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        List<String> videoIdList = new ArrayList<>();

        for (int i = 0; i <eduVideos.size() ; i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            videoIdList.add(videoSourceId);
        }

        if (videoIdList.size()>0){
            //删除多个视频
            vodClient.removeVideos(videoIdList);
        }

        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        baseMapper.delete(eduVideoQueryWrapper);
    }

    //根据课时id找到视频id
    @Override
    public String getVideoSourceId(String videoId) {
        EduVideo eduVideo = baseMapper.selectById(videoId);
        return eduVideo.getVideoSourceId();
    }
}
