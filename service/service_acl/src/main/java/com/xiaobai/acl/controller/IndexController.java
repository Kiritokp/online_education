package com.xiaobai.acl.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.acl.service.IndexService;
import com.xiaobai.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobai
 * @create 2021-09-01 18:09
 */
@Api(description = "登录管理")
@RestController
@RequestMapping("admin/acl/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    @ApiOperation("根据token获取用户信息")
    @GetMapping("info")
    public R info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return R.ok().data(userInfo);
    }

    @ApiOperation("获取菜单")
    @GetMapping("menu")
    public R getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return R.ok().data("permissionList", permissionList);
    }

    @ApiOperation("登出")
    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
