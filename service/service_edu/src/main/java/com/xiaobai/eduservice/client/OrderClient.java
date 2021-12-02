package com.xiaobai.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiaobai
 * @create 2021-08-24 13:16
 */
@Component
@FeignClient(name = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient {

    //根据课程id和用户id查询订单表中的订单状态
    @GetMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId);
}
