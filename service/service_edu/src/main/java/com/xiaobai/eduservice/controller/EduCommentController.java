package com.xiaobai.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaobai.eduservice.client.UcenterClient;
import com.xiaobai.eduservice.entity.EduComment;
import com.xiaobai.eduservice.entity.vo.UcenterMemberOrder;
import com.xiaobai.eduservice.service.EduCommentService;
import com.xiaobai.eduservice.utils.JwtUtils;
import com.xiaobai.eduservice.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-17
 */
@Api(description = "评论管理")
@RestController
@RequestMapping("/eduservice/comment")
public class EduCommentController {
    @Autowired
    private EduCommentService eduCommentService;
    @Autowired
    private UcenterClient ucenterClient;

    @ApiOperation(value = "根据课程id查询并分页评论列表")
    @GetMapping("getCommentPage/{page}/{limit}")
    public R getCommentPage(@PathVariable Long page, @PathVariable Long limit, String courseId){
        Page<EduComment> eduCommentPage = new Page<>(page,limit);
        Map<String,Object> map=eduCommentService.selectCommentPage(eduCommentPage,courseId);
        return R.ok().data(map);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping("addComment")
    //public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request){
    public R addComment(@RequestBody EduComment eduComment){
        /*//根据token获取用户的id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //判断用户是否登录
        if (StringUtils.isEmpty(memberId)){
            return R.error().code(20001).message("请登录！");
        }
        //根据memberId远程调用获取用户信息
        UcenterMemberOrder member = ucenterClient.getMemberById(memberId);*/

        UcenterMemberOrder member = new UcenterMemberOrder();
        member.setNickname("小白");
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        //把获取的用户信息赋值给eduComment
        eduComment.setMemberId("1");
        eduComment.setNickname(member.getNickname());
        eduComment.setAvatar(member.getAvatar());

        //保存到数据库
        eduCommentService.save(eduComment);
        return R.ok();
    }
}

