package com.xiaobai.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.commonutils.JwtUtils;
import com.xiaobai.commonutils.R;
import com.xiaobai.eduservice.client.OrderClient;
import com.xiaobai.eduservice.entity.EduCourse;
import com.xiaobai.eduservice.entity.chapter.ChapterVo;
import com.xiaobai.eduservice.entity.frontvo.CourseQueryVo;
import com.xiaobai.eduservice.entity.frontvo.CourseWebVo;
import com.xiaobai.eduservice.service.EduChapterService;
import com.xiaobai.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author xiaobai
 * @create 2021-08-16 11:17
 */
@Api(description = "前台课程管理")
@RestController
@RequestMapping("eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private OrderClient orderClient;

    @ApiOperation(value = "课程条件查询带分页")
    @PostMapping("getCoursePage/{page}/{limit}")
    public R getCoursePage(@PathVariable long page, @PathVariable long limit,
                           @RequestBody(required = false) CourseQueryVo courseQueryVo){
        Page<EduCourse> eduCoursePage = new Page<>(page, limit);
        Map<String,Object> map=eduCourseService.selectCoursePage(eduCoursePage,courseQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据id查询课程")
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id, HttpServletRequest request){
        //查询课程信息和讲师信息
        CourseWebVo courseWebVo=eduCourseService.selectInfoWebById(id);
        //查询当前课程的章节信息
        List<ChapterVo> chapterVideoList = eduChapterService.geChapterVideoByCourseId(id);
        //远程调用，判断课程是否被购买
        //orderClient.isBuyCourse(id,JwtUtils.getMemberIdByJwtToken(request));
        boolean buyCourse = orderClient.isBuyCourse(id, "1111");

        return R.ok().data("chapterVideoList",chapterVideoList).data("courseWebVo",courseWebVo).data("isBuy",buyCourse);
    }

}
