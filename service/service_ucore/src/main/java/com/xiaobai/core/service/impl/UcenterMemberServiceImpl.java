package com.xiaobai.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.core.entity.UcenterMember;
import com.xiaobai.core.entity.vo.LoginVo;
import com.xiaobai.core.entity.vo.RegisterVo;
import com.xiaobai.core.mapper.UcenterMemberMapper;
import com.xiaobai.core.service.UcenterMemberService;
import com.xiaobai.core.utils.JwtUtils;
import com.xiaobai.core.utils.MD5;
import com.xiaobai.servicebase.exceptionhandle.CustomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-10
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //用户登录
    @Override
    public String login(LoginVo loginVo) {
        //获得参数
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new CustomException(200001,"手机号或密码为空！");
        }

        //获取用户
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        if (ucenterMember==null){
            throw new CustomException(20001,"查无此人！");
        }
        //校验密码
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw new CustomException(20001,"密码错误！");
        }
        //检查是否被禁用
        if (ucenterMember.getIsDisabled()){
            throw new CustomException(20001,"用户被禁用！");
        }

        //使用Jwt生成token字符串
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return token;
    }

    //用户注册
    @Override
    public boolean register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        //校验参数
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(nickname)
                || StringUtils.isEmpty(password)){
            throw new CustomException(20001,"用户信息为空！");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);

        if (!code.equals(redisCode)){
            throw new CustomException(2001,"验证码错误！");
        }
        //查询数据库中是否存在相同的手机号码
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0){
            throw new CustomException(20001,"手机已存在！");
        }
        //添加注册信息到数据库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));

        int insert = baseMapper.insert(ucenterMember);
        if (insert>0){
            return true;
        }
        return false;
    }

    //获得openid
    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }

    //统计某一天的注册人数
    @Override
    public Integer getCountMember(String day) {
        return baseMapper.selectCountRegister(day);
    }
}
