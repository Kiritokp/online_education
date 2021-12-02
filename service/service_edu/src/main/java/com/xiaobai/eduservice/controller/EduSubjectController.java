package com.xiaobai.eduservice.controller;


import com.xiaobai.commonutils.R;
import com.xiaobai.eduservice.entity.EduSubject;
import com.xiaobai.eduservice.entity.subject.OneSubject;
import com.xiaobai.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-20
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件,把文件读取出来
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        //上传excel过来的文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //显示课程列表（树形）
    @ApiOperation(value = "显示课程列表")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //list集合泛型时一级分类
        List<OneSubject> list=eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "查询所有的一级分类")
    @GetMapping("getAllOneSubject")
    public R getAllOneSubject(){
        List<EduSubject> list=eduSubjectService.findAllOneSubject();
        return R.ok().data("oneSubjectList",list);
    }

    @ApiOperation(value = "根据一级分类的id查询所有的二级分类")
    @GetMapping("getTwoSubject/{id}")
    public R getAllOTwoSubject(@ApiParam(name = "id",value = "一级分类id",required = true) @PathVariable("id") String id){
        List<EduSubject> list=eduSubjectService.findTwoSubject(id);
        return R.ok().data("twoSubjectList",list);
    }

}

