package com.xiaobai.statistics.controller;


import com.xiaobai.commonutils.R;
import com.xiaobai.statistics.client.UcoreClient;
import com.xiaobai.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-24
 */
@Api(description = "统计分析")
@RestController
@RequestMapping("/edustatistics/daily")
public class DailyController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation(value ="远程调用ucore获取数据存入统计表中" )
    @GetMapping("statistics/{day}")
    public R createStatisticsByDate(@PathVariable String day){
        dailyService.createStatisticsByDate(day);
        return R.ok();
    }

    @ApiOperation(value = "显示图表")
    @GetMapping("showChart/{begin}/{end}")
    public R showChart(@PathVariable String begin,@PathVariable String end){
        if (StringUtils.isEmpty(begin)){
            begin="1949-10-01";
        }else if(StringUtils.isEmpty(end)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            end=sdf.format(date);
        }
        Map<String, Object> map = dailyService.getChartData(begin, end);
        return R.ok().data(map);
    }

}

