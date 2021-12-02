package com.xiaobai.eduservice.controller;


import com.xiaobai.commonutils.R;
import com.xiaobai.eduservice.client.VodClient;
import com.xiaobai.eduservice.entity.EduVideo;
import com.xiaobai.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
@Api(description = "课时管理")
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @ApiOperation(value = "添加课时")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);
        if (!save){
            return R.error();
        }
        return R.ok();
    }

    @ApiOperation(value = "删除课时并删除对应的阿里云视频")
    @GetMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId){
        //根据课时id找到视频id
        String videoSourceId=eduVideoService.getVideoSourceId(videoId);
        //判断课时里面是否有视频id
        if (!StringUtils.isEmpty(videoSourceId)){
            //根据视频id远程调用删除视频
            vodClient.removeVideo(videoSourceId);
        }
        //删除课时
        boolean remove = eduVideoService.removeById(videoId);
        if (!remove){
            return R.error();
        }
        return R.ok();
    }

    @ApiOperation(value = "修改课时")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean update = eduVideoService.updateById(eduVideo);
        if (!update){
            return R.error();
        }
        return R.ok();
    }

    @ApiOperation(value = "根据课时id查询课时")
    @GetMapping("getVideo/{videoId}")
    public R getVideo(@PathVariable("videoId") String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

}

