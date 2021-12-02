package com.xiaobai.eduservice.client;

import com.xiaobai.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xiaobai
 * @create 2021-08-03 9:34
 */
@Component
@FeignClient(name="service-vod",fallback = VodClientImpl.class) //调用服务的名称
public interface VodClient {
    //定义调用的方法路径
    //根据视频id删除阿里云视频
    //@PathVariable注解一定要指定参数名称，否则出错
    //远程调用删除单个阿里云视频
    @DeleteMapping("eduvod/video/removeVideo/{id}")
    public R removeVideo(@PathVariable("id") String id);

    //远程调用删除多个阿里云视频
    @DeleteMapping("eduvod/video/removeVideos")
    public R removeVideos(@RequestParam("videoIdList") List<String> videoIdList);
}
