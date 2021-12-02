package com.xiaobai.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobai.commonutils.JwtUtils;
import com.xiaobai.commonutils.R;
import com.xiaobai.order.client.EduClient;
import com.xiaobai.order.client.UcoreClient;
import com.xiaobai.order.entity.Order;
import com.xiaobai.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-19
 */
@Api(description = "微信支付管理")
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "根据课程id和用户id创建订单，返回订单id")
    @GetMapping("getOrderId/{courseId}")
    public R getOrderId(@PathVariable("courseId") String courseId, HttpServletRequest request){
        String orderId=orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderId);
    }

    @ApiOperation(value = "根据订单id获取订单信息")
    @GetMapping("getOrderInfo/{id}")
    public R getOrderInfo(@PathVariable("id") String id){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",id);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    @ApiOperation(value = "根据课程id和用户id查询订单表中的订单状态")
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);

        int count = orderService.count(wrapper);
        return count>0;
    }

}

