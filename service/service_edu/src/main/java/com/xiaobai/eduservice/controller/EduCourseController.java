package com.xiaobai.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.commonutils.R;
import com.xiaobai.eduservice.client.UcenterClient;
import com.xiaobai.eduservice.entity.EduCourse;
import com.xiaobai.eduservice.entity.vo.*;
import com.xiaobai.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-07-22
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "获得课程列表")
    @GetMapping("getCourseList")
    public R getCourseList(){
        List<EduCourse> eduCourseList = eduCourseService.list(null);
        return R.ok().data("eduCourseList",eduCourseList);
    }

    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后的课程id，为了后面添加大纲使用
        String id=eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    @ApiOperation(value = "根据课程id查询课程的基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable("courseId") String courseId){
        CourseInfoVo courseInfoVo=eduCourseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据课程id查询最终确认信息")
    @GetMapping("getCoursePublishVo/{courseId}")
    public R getCoursePublishVo(@PathVariable("courseId") String courseId){
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVo(courseId);
        return R.ok().data("coursePublishVo",coursePublishVo);
    }

    @ApiOperation(value = "根据id修改课程状态，最终发布课程")
    @GetMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable("courseId") String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean update = eduCourseService.updateById(eduCourse);
        if (!update){
            return R.error();
        }
        return R.ok();
    }

    @ApiOperation(value = "课程条件查询带分页")
    @PostMapping("getCoursePageList/{current}/{limit}")
    public R getCoursePageList(@PathVariable("current") Long current,@PathVariable("limit") Long limit,@RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }

        eduCourseService.page(pageCourse,wrapper);
        long total=pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        wrapper.orderByDesc("gmt_create");
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "删除课程")
    @GetMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable("courseId")String courseId){
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }

    @ApiOperation(value = "根据课程id查询课程的基本信息")
    @GetMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id){
        return eduCourseService.getCourseOrderById(id);
    }


}

