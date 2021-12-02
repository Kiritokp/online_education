package com.xiaobai.security.security;

import com.xiaobai.commonutils.R;
import com.xiaobai.commonutils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 登出业务逻辑类
 * 退出处理器
 * </p>
 */
public class TokenLogoutHandler implements LogoutHandler {

    private TokenManager tokenManager;
    private RedisTemplate redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //从header中获得token
        String token = request.getHeader("token");
        if (token != null) {
            //移除
            tokenManager.removeToken(token);

            //从token中获取用户信息
            String userName = tokenManager.getUserFromToken(token);
            //清空当前用户缓存中的权限数据
            redisTemplate.delete(userName);
        }
        ResponseUtil.out(response, R.ok());
    }

}