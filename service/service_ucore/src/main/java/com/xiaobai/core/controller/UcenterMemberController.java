package com.xiaobai.core.controller;


import com.xiaobai.commonutils.R;
import com.xiaobai.core.entity.UcenterMember;
import com.xiaobai.core.entity.vo.LoginVo;
import com.xiaobai.core.entity.vo.RegisterVo;
import com.xiaobai.core.entity.vo.UcenterMemberOrder;
import com.xiaobai.core.service.UcenterMemberService;
import com.xiaobai.core.utils.JwtUtils;
import com.xiaobai.servicebase.exceptionhandle.CustomException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-10
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/educore/ucenter")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo){
        String token=ucenterMemberService.login(loginVo);
        return R.ok().data("token",token);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        boolean isRegister=ucenterMemberService.register(registerVo);
        if (!isRegister){
            throw new CustomException(20001,"注册失败！");
        }
        return R.ok().message("注册成功！");
    }

    @ApiOperation(value = "根据token获取用户信息")
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }

    @ApiOperation(value = "根据id获取用户信息")
    @GetMapping("getMemberById/{id}")
    public UcenterMemberOrder getMemberById(@PathVariable String id){
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @ApiOperation(value = "统计某一天的注册人数")
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable("day") String day){
        Integer count=ucenterMemberService.getCountMember(day);
        return R.ok().data("countRegister",count);
    }

}

