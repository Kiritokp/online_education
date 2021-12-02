package com.xiaobai.acl.service;

import com.alibaba.fastjson.JSONObject;
import com.xiaobai.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-30
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllMenu();

    boolean deleteMenu(String id);

    void saveRolePermissionRelationShip(String roleId, String[] permissionIds);

    List<Permission> selectAllMenu(String roleId);

    List<String> selectPermissionValueByUserId(String userId);

    List<JSONObject> selectPermissionByUserId(String id);
}
