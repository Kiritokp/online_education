package com.xiaobai.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobai.acl.entity.Role;
import com.xiaobai.acl.entity.UserRole;
import com.xiaobai.acl.mapper.RoleMapper;
import com.xiaobai.acl.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.acl.service.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private UserRoleService userRoleService;

    //根据用户获取角色数据
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        //查询所有的角色
        List<Role> allRolesList = baseMapper.selectList(null);
        //根据用户id，查询用户拥有的角色id
        List<UserRole> existUserRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));
        List<String> existRoleList = existUserRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        //对角色进行分类
        ArrayList<Role> assignRoles = new ArrayList<>();
        for (Role role : allRolesList) {
            if (existRoleList.contains(role.getId())){
                allRolesList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    //根据用户分配角色   一  =>  多
    @Override
    public void saveUserRoleRelationShip(String userId, String[] roleIds) {
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id",userId));

        ArrayList<UserRole> userRoleList = new ArrayList<>();
        for (String roleId : roleIds) {
            if (StringUtils.isEmpty(roleId)) continue;
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);

            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }

    /**
     * 根据用户id获得用户所属的角色
     * @param userId
     * @return
     */
    @Override
    public List<Role> selectRoleByUserId(String userId) {
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());

        List<Role> roleList = new ArrayList<>();
        if (roleIdList.size()>0){
            roleList=baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
