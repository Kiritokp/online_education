package com.xiaobai.statistics.service;

import com.xiaobai.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-24
 */
public interface DailyService extends IService<Daily> {

    void createStatisticsByDate(String day);

    Map<String, Object> getChartData(String begin, String end);
}
