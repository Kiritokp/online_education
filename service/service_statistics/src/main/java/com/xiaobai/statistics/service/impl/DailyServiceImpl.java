package com.xiaobai.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaobai.statistics.client.UcoreClient;
import com.xiaobai.statistics.entity.Daily;
import com.xiaobai.statistics.mapper.DailyMapper;
import com.xiaobai.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author xiaobai
 * @since 2021-08-24
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    private UcoreClient ucoreClient;

    //远程调用ucore获取数据存入统计表中
    @Override
    public void createStatisticsByDate(String day) {
        //删除已存在的统计对象
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        //获取统计信息
        Integer registerNum=(Integer) ucoreClient.countRegister(day).getData().get("countRegister");
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO need redis configuration
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO need redis configuration
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO need redis configuration
        //创建统计对象
        Daily daily = new Daily();

        daily.setRegisterNum(registerNum);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        //保存到数据库
        baseMapper.insert(daily);
    }

    //显示图表
    @Override
    public Map<String, Object> getChartData(String begin, String end) {

        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        //wrapper.select("date_calculated");
        wrapper.between("date_calculated",begin,end);

        List<Daily> dailyList = baseMapper.selectList(wrapper);

        Map<String, Object> map = new HashMap<>();

        List<String> dateList = new ArrayList<>();
        List<Integer> registerList = new ArrayList<>();
        List<Integer> loginList = new ArrayList<>();
        List<Integer> videoList = new ArrayList<>();
        List<Integer> courseList = new ArrayList<>();

        map.put("dateList", dateList);
        map.put("registerList", registerList);
        map.put("loginList", loginList);
        map.put("videoList", videoList);
        map.put("courseList", courseList);

        for (int i = 0; i < dailyList.size(); i++) {
            Daily daily = dailyList.get(i);
            dateList.add(daily.getDateCalculated());
            registerList.add(daily.getRegisterNum());
            loginList.add(daily.getLoginNum());
            videoList.add(daily.getVideoViewNum());
            courseList.add(daily.getCourseNum());
        }
        return map;

        /*//type不为All的时候
        wrapper.select(type,"date_calculated");
        wrapper.between("date_calculated",begin,end);

        List<Daily> dailyList = baseMapper.selectList(wrapper);

        Map<String, Object> map = new HashMap<>();

        List<Integer> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);

        for (int i = 0; i < dailyList.size(); i++) {
            Daily daily = dailyList.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;*/
    }
}
