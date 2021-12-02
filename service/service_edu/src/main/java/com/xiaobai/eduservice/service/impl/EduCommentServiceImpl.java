package com.xiaobai.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.eduservice.entity.EduComment;
import com.xiaobai.eduservice.mapper.EduCommentMapper;
import com.xiaobai.eduservice.service.EduCommentService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-17
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    //根据课程id查询评论列表并分页
    @Override
    public Map<String, Object> selectCommentPage(Page<EduComment> eduCommentPage, String courseId) {
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(eduCommentPage,wrapper);

        List<EduComment> commentList = eduCommentPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", eduCommentPage.getCurrent());
        map.put("pages", eduCommentPage.getPages());
        map.put("size", eduCommentPage.getSize());
        map.put("total", eduCommentPage.getTotal());
        map.put("hasNext", eduCommentPage.hasNext());
        map.put("hasPrevious", eduCommentPage.hasPrevious());
        return map;
    }
}
