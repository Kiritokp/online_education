package com.xiaobai.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.commonutils.R;
import com.xiaobai.eduservice.entity.EduTeacher;
import com.xiaobai.eduservice.entity.vo.TeacherQuery;
import com.xiaobai.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-05-23
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    //查询讲师的所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public R list(){
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //逻辑删除讲师
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/deleteTeacher/{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable("id") String id){
        boolean flag = teacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name="current",value = "当前页码",required = true)
                          @PathVariable Long current,
                      @ApiParam(name = "limit",value = "每页的记录数",required = true)
                      @PathVariable Long limit){
        Page<EduTeacher> pageParam = new Page<>(current, limit);
        //统一异常处理测试
        /*try {
            int i=10/0;
        } catch (Exception e) {
            throw new CustomException(20001,"执行了自定义异常处理");
        }*/

        teacherService.page(pageParam,null);

        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        /*HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);

        return R.ok().data(map);*/

        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "讲师条件查询带分页")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current",value = "当前页码",required = true)
                                      @PathVariable Long current,
                                  @ApiParam(name = "limit",value = "每页的记录数",required = true)
                                  @PathVariable Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断是否为空，如果不为空则拼接条件
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);   // ==
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);   //  >=  表中的字段名称
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);    // <=
        }
        wrapper.orderByDesc("gmt_create");
        //调用方法实现分页查询
        teacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        if(total==0){
            return R.error().message("查询失败！");
        }
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@ApiParam(name="teacher",value = "讲师对象",required = true)@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id",value = "讲师ID",required = true)@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        if (teacher==null){
            return R.error().message("查询失败");
        }
        return R.ok().data("teacher",teacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@ApiParam(name = "teacher",value = "讲师对象",required = true)@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

}

