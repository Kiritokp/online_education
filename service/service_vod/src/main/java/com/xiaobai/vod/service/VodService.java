package com.xiaobai.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xiaobai
 * @create 2021-08-02 10:32
 */
public interface VodService {
    String uploadVideo(MultipartFile file);

    void removeVideo(String id);

    void removeVideos(List<String> videoIdList);

    String getPlayAuth(String id);
}
