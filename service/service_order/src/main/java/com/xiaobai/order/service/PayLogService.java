package com.xiaobai.order.service;

import com.xiaobai.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-19
 */
public interface PayLogService extends IService<PayLog> {

    Map<String, Object> createNative(String orderNo);

    Map<String, String> getPayStatus(String orderNo);

    void updateStatus(Map<String, String> map);
}
