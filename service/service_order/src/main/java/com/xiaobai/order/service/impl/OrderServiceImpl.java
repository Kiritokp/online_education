package com.xiaobai.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobai.commonutils.R;
import com.xiaobai.order.client.EduClient;
import com.xiaobai.order.client.UcoreClient;
import com.xiaobai.order.entity.CourseWebVoOrder;
import com.xiaobai.order.entity.Order;
import com.xiaobai.order.entity.UcenterMemberOrder;
import com.xiaobai.order.mapper.OrderMapper;
import com.xiaobai.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaobai.order.utils.OrderNoUtil;
import com.xiaobai.servicebase.exceptionhandle.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-19
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcoreClient ucoreClient;
    //根据课程id和用户id创建订单，返回订单id
    @Override
    public String saveOrder(String courseId, String memberIdByJwtToken) {
        //远程调用课程服务，根据课程id获取课程信息
        CourseWebVoOrder courseWebVoOrder = eduClient.getCourseInfoOrder(courseId);
        System.out.println(courseWebVoOrder);
        if (courseWebVoOrder==null){
            throw new CustomException(20001,"远程服务调用出错！");
        }

        //远程调用用户服务，根据用户id获取用户信息
        //UcenterMemberOrder memberById = ucoreClient.getMemberById(memberIdByJwtToken);

        UcenterMemberOrder memberById = new UcenterMemberOrder();
        memberById.setNickname("小白");
        memberById.setMobile("18739172639");

        //创建订单向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId);//课程id
        order.setCourseTitle(courseWebVoOrder.getTitle());
        order.setCourseCover(courseWebVoOrder.getCover());
        order.setTeacherName(courseWebVoOrder.getTeacherName());
        order.setTotalFee(courseWebVoOrder.getPrice());
        order.setMemberId("1111");
        order.setMobile(memberById.getMobile());
        order.setNickname(memberById.getNickname());
        order.setStatus(0);//订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
