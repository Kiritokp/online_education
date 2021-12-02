package com.xiaobai.statistics.task;

import com.xiaobai.statistics.service.DailyService;
import com.xiaobai.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xiaobai
 * @create 2021-08-25 1:01
 */
@Component
public class ScheduledTask {
    @Autowired
    private DailyService dailyService;

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.createStatisticsByDate(day);
    }
}
