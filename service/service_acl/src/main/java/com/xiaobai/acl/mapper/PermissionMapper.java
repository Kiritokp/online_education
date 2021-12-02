package com.xiaobai.acl.mapper;

import com.xiaobai.acl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-30
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<String> selectAllPermissionValue();

    List<String> selectPermissionValueByUserId(String userId);

    List<Permission> selectPermissionByUserId(String id);
}
