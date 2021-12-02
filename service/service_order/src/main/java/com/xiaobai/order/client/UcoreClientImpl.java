package com.xiaobai.order.client;

import com.xiaobai.order.entity.UcenterMemberOrder;
import org.springframework.stereotype.Component;

/**
 * @author xiaobai
 * @create 2021-08-19 17:29
 */
@Component
public class UcoreClientImpl implements UcoreClient{
    @Override
    public UcenterMemberOrder getMemberById(String id) {
        System.out.println("服务降级了~~~");
        return null;
    }
}
