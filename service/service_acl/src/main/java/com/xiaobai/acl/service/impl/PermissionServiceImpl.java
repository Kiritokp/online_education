package com.xiaobai.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobai.acl.entity.Permission;
import com.xiaobai.acl.entity.RolePermission;
import com.xiaobai.acl.entity.User;
import com.xiaobai.acl.mapper.PermissionMapper;
import com.xiaobai.acl.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.acl.service.RolePermissionService;
import com.xiaobai.acl.service.UserService;
import com.xiaobai.acl.utils.MenuConfig;
import com.xiaobai.acl.utils.PermissionConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;

    //查询全部菜单
    @Override
    public List<Permission> queryAllMenu() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> treeNodes = baseMapper.selectList(wrapper);

        //递归查询
        return PermissionConfig.build(treeNodes);
    }

    //递归删除菜单
    @Override
    public boolean deleteMenu(String id) {
        List<String> idList = new ArrayList<>();
        this.selectChildrenListById(id,idList);
        //把根节点放到list中
        idList.add(id);

        //根据根节点的id递归查询到所有children的id都放到list中,同时删除
        int i = baseMapper.deleteBatchIds(idList);

        return i>0;
    }

    //给角色分配权限
    @Override
    public void saveRolePermissionRelationShip(String roleId, String[] permissionIds) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        rolePermissionService.remove(wrapper);

        List<RolePermission> rolePermissionList = new ArrayList<>();

        for (String permissionId : permissionIds) {
            if (StringUtils.isEmpty(permissionId)) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);

            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("CAST(id as SIGNED)");
        List<Permission> allPermissionList = baseMapper.selectList(wrapper);
        List<Permission> list = new ArrayList<>();

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int j = 0; j < rolePermissionList.size(); j++) {
                RolePermission rolePermission = rolePermissionList.get(j);
                if (rolePermission.getPermissionId().equals(permission.getId())){
                    //如果相等则说明已有这个权限
                    permission.setSelect(true);

                    list.add(permission);
                }
            }
        }

        List<Permission> permissionList = PermissionConfig.build(list);
        return permissionList;
    }

    /**
     * 根据用户id获取操作权限值
     * @param userId
     * @return
     */
    @Override
    public List<String> selectPermissionValueByUserId(String userId) {
        List<String> selectPermissionValueList = null;
        if (this.isSysAdmin(userId)){
            //如果是系统管理员，获取所有权限
            selectPermissionValueList=baseMapper.selectAllPermissionValue();
        }else {
            selectPermissionValueList=baseMapper.selectPermissionValueByUserId(userId);
        }
        return selectPermissionValueList;
    }

    /**
     * 根据用户id获取用户菜单权限
     * @param id
     * @return
     */
    @Override
    public List<JSONObject> selectPermissionByUserId(String id) {
        List<Permission> selectPermissionList = null;
        if (this.isSysAdmin(id)){
            //如果是超级管理员，获取所有菜单
            selectPermissionList=baseMapper.selectList(null);
        }else {
            selectPermissionList=baseMapper.selectPermissionByUserId(id);
        }
        List<Permission> permissionList = PermissionConfig.build(selectPermissionList);
        List<JSONObject> result = MenuConfig.bulid(permissionList);

        return result;
    }

    /**
     * 递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildrenListById(String id,List<String> idList){
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",id);
        wrapper.select("id");
        List<Permission> childList = baseMapper.selectList(wrapper);

        childList.forEach(item->{
            idList.add(item.getId());
            this.selectChildrenListById(item.getId(),idList);
        });
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);
        if (user!=null && ("admin").equals(user.getUsername())){
            return true;
        }
        return false;
    }
}
