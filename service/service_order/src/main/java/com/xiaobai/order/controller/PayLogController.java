package com.xiaobai.order.controller;


import com.xiaobai.commonutils.R;
import com.xiaobai.order.service.PayLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-19
 */
@Api(description = "微信支付二维码")
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    @ApiOperation(value = "生成二维码")
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map<String,Object> map=payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据订单id获取支付的状态")
    @GetMapping("getPayStatus/{orderNo}")
    public R getPayStatus(@PathVariable String orderNo){
        Map<String,String> map=payLogService.getPayStatus(orderNo);
        if (map==null){
            return R.error().message("支付出错！");
        }
        //如果成功，更改支付的状态
        if (map.get("trade_state").equals("SUCCESS")){
            payLogService.updateStatus(map);
            //todo支付完成以后课程的buy_count字段+1
            return R.ok().message("支付成功！");
        }
        return R.ok().code(25000).message("支付中！");
    }
}

