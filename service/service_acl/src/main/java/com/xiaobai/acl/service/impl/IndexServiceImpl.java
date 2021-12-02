package com.xiaobai.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.acl.entity.Role;
import com.xiaobai.acl.entity.User;
import com.xiaobai.acl.service.IndexService;
import com.xiaobai.acl.service.PermissionService;
import com.xiaobai.acl.service.RoleService;
import com.xiaobai.acl.service.UserService;
import com.xiaobai.servicebase.exceptionhandle.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaobai
 * @create 2021-09-01 18:11
 */
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {
        HashMap<String, Object> result = new HashMap<>();
        User user=userService.selectByUsername(username);
        if (user==null){
            throw new CustomException(20001,"查无此人！");
        }
        //根据用户的id获取角色
        List<Role> roleList=roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if (roleNameList.size()==0){
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }
        //根据用户id获取操作权限值
        List<String> permissionValueList=permissionService.selectPermissionValueByUserId(user.getId());
        //存入redis
        redisTemplate.opsForValue().set(username,permissionValueList);
        result.put("name",user.getUsername());
        result.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles",roleNameList);
        result.put("permissionValueList",permissionValueList);

        return result;
    }
    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    @Override
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);
        //根据用户id获取用户菜单权限
        List<JSONObject> permissionList=permissionService.selectPermissionByUserId(user.getId());
        return permissionList;
    }
}
