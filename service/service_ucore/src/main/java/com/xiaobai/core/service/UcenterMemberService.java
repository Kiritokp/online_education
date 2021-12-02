package com.xiaobai.core.service;

import com.xiaobai.core.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaobai.core.entity.vo.LoginVo;
import com.xiaobai.core.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-10
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    boolean register(RegisterVo registerVo);

    UcenterMember getByOpenid(String openid);

    Integer getCountMember(String day);
}
