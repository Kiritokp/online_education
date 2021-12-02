package com.xiaobai.eduservice.controller.front;

import com.xiaobai.commonutils.R;
import com.xiaobai.eduservice.entity.EduCourse;
import com.xiaobai.eduservice.entity.EduTeacher;
import com.xiaobai.eduservice.service.EduCourseService;
import com.xiaobai.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaobai
 * @create 2021-08-05 16:57
 */
@Api(description = "前台首页管理")
@RestController
@RequestMapping("eduservice/indexfront")
public class IndexFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "获取8个热门课程和4个名师")
    @GetMapping("/index")
    public R index(){
        List<EduCourse> courseList=eduCourseService.selectPopularCourse();
        List<EduTeacher> teacherList=eduTeacherService.selectPopularTeacher();
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
