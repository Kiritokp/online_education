package com.xiaobai.eduservice.client;

import com.xiaobai.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaobai
 * @create 2021-08-03 13:56
 */
@Component
public class VodClientImpl implements VodClient {

    //服务降级
    @Override
    public R removeVideo(String id) {
        return R.error().message("删除视频出错了！");
    }

    @Override
    public R removeVideos(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了！");
    }
}
