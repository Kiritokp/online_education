package com.xiaobai.statistics.client;

import com.xiaobai.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiaobai
 * @create 2021-08-25 0:41
 */
@Component
@FeignClient(name = "service-ucore",fallback = UcoreClientImpl.class)
public interface UcoreClient {

    //统计某一天的注册人数
    @GetMapping("/educore/ucenter/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
