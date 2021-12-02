package com.xiaobai.statistics.client;

import com.xiaobai.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @author xiaobai
 * @create 2021-08-25 0:41
 */
@Component
public class UcoreClientImpl implements UcoreClient{
    @Override
    public R countRegister(String day) {
        System.out.println("服务降级了~~~");
        return null;
    }
}
