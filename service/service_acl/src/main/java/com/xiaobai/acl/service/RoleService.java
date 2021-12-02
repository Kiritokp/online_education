package com.xiaobai.acl.service;

import com.xiaobai.acl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-30
 */
public interface RoleService extends IService<Role> {

    Map<String, Object> findRoleByUserId(String userId);

    void saveUserRoleRelationShip(String userId, String[] roleId);

    List<Role> selectRoleByUserId(String userId);
}
