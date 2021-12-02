package com.xiaobai.order.client;

import com.xiaobai.order.entity.UcenterMemberOrder;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiaobai
 * @create 2021-08-19 17:29
 */
@Component
@FeignClient(name = "service-ucore",fallback = UcoreClientImpl.class)
public interface UcoreClient {

    //根据id获取用户信息
    @GetMapping("/educore/ucenter/getMemberById/{id}")
    public UcenterMemberOrder getMemberById(@PathVariable String id);
}
