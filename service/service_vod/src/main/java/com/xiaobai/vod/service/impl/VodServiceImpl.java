package com.xiaobai.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.xiaobai.servicebase.exceptionhandle.CustomException;
import com.xiaobai.vod.service.VodService;
import com.xiaobai.vod.utils.ConstantVodUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author xiaobai
 * @create 2021-08-02 10:32
 */
@Service
public class VodServiceImpl implements VodService {

    //上传视频
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            String fileName=file.getOriginalFilename();
            String title=fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantVodUtils.ACCESS_KEY_ID,
                    ConstantVodUtils.ACCESS_KEY_SECRET,
                    title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = response.getVideoId();

            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //根绝视频id删除阿里云视频
    @Override
    public void removeVideo(String id)  {
        try {
            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(id);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(20001,"删除失败！");
        }
    }

    //删除多个阿里云视频
    @Override
    public void removeVideos(List<String> videoIdList) {
        try {
            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            String join = StringUtils.join(videoIdList.toArray(), ",");
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(join);
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(20001,"删除失败！");
        }
    }

    //根据视频id获取视频凭证
    @Override
    public String getPlayAuth(String id) {
        try {
            //初始化
            DefaultAcsClient client = initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //得到播放凭证
            String playAuth = response.getPlayAuth();

            return playAuth;
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(20001,"获取视频凭证失败！");
        }
    }


    //初始化
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
