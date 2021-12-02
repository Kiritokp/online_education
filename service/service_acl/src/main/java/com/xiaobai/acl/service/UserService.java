package com.xiaobai.acl.service;

import com.xiaobai.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-30
 */
public interface UserService extends IService<User> {

    User selectByUsername(String username);
}
