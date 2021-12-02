package com.xiaobai.core.mapper;

import com.xiaobai.core.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-10
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer selectCountRegister(String day);
}
