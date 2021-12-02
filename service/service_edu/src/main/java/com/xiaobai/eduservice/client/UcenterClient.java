package com.xiaobai.eduservice.client;

import com.xiaobai.eduservice.entity.vo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiaobai
 * @create 2021-08-17 15:55
 */
@Component
@FeignClient(name = "service-ucore",fallback =UcenterClientImpl.class ) //调用服务的名称
public interface UcenterClient {

    //根据用户id获取用户信息
    @GetMapping("/educore/ucenter/getMemberById/{id}")
    public UcenterMemberOrder getMemberById(@PathVariable String id);
}
