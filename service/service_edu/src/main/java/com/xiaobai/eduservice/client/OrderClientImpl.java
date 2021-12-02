package com.xiaobai.eduservice.client;

import org.springframework.stereotype.Component;

/**
 * @author xiaobai
 * @create 2021-08-24 13:17
 */
@Component
public class OrderClientImpl implements OrderClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        System.out.println("服务降级了~~~");
        return false;
    }
}
