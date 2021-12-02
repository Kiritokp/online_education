package com.xiaobai.vod.controller;

import com.xiaobai.commonutils.R;
import com.xiaobai.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xiaobai
 * @create 2021-08-02 10:33
 */
@Api(description = "阿里云视频管理")
@RestController
@RequestMapping("eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation(value = "上传视频")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId=vodService.uploadVideo(file);
        return R.ok().message("视频上传成功！").data("videoId",videoId);
    }

    @ApiOperation(value = "根据视频id删除阿里云视频")
    @DeleteMapping("removeVideo/{id}")
    public R removeVideo(@PathVariable("id") String id){
        vodService.removeVideo(id);
        return R.ok();
    }

    @ApiOperation(value = "删除多个阿里云视频的方法")
    @DeleteMapping("removeVideos")
    public R removeVideos(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeVideos(videoIdList);
        return R.ok();
    }

    @ApiOperation(value = "根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        String playAuth=vodService.getPlayAuth(id);
        return R.ok().data("playAuth",playAuth);
    }
}
