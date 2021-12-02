package com.xiaobai.eduservice.controller;


import com.xiaobai.commonutils.R;
import com.xiaobai.eduservice.entity.EduChapter;
import com.xiaobai.eduservice.entity.chapter.ChapterVo;
import com.xiaobai.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(description = "章节管理")
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "根据课程id获取所有章节和小结")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list= eduChapterService.geChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询章节")
    @GetMapping("getChapter/{chapterId}")
    public R getChapterById(@PathVariable("chapterId") String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        boolean flag = eduChapterService.updateById(eduChapter);
        if (!flag){
            return R.error();
        }
        return R.ok();
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        boolean flag=eduChapterService.deleteChapter(chapterId);
        if (!flag){
            return R.error();
        }
        return R.ok();
    }
}

