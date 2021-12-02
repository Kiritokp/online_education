package com.xiaobai.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaobai
 * @create 2021-07-19 9:27
 */
public interface FileService {
    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
