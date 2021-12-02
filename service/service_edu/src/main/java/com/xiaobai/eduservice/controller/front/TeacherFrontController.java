package com.xiaobai.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.commonutils.R;
import com.xiaobai.eduservice.entity.EduCourse;
import com.xiaobai.eduservice.entity.EduTeacher;
import com.xiaobai.eduservice.service.EduCourseService;
import com.xiaobai.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobai
 * @create 2021-08-13 10:40
 */
@Api(description = "前台讲师管理")
@RestController
@RequestMapping("eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "讲师分页查询")
    @GetMapping("getTeacherPage/{page}/{limit}")
    public R getTeacherPage(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map=eduTeacherService.selectTeacherPage(teacherPage);
        return R.ok().data(map);
    }

    @ApiOperation(value = "讲师详情")
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable long id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        //根据讲师id查询讲师所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> list = eduCourseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",list);
    }
}
