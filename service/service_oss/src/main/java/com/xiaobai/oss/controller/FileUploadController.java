package com.xiaobai.oss.controller;

import com.xiaobai.commonutils.R;
import com.xiaobai.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xiaobai
 * @create 2021-07-19 9:49
 */
@Api(description = "阿里云文件管理")
@RestController
@RequestMapping("/eduoss/file")
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("/upload")
    public R upload(@ApiParam(name="file",value = "文件",required = true)
                        @RequestParam("file") MultipartFile file){
        String url=fileService.upload(file);
        return  R.ok().message("文件上传成功").data("url",url);
    }


}
