package com.xiaobai.order.client;

import com.xiaobai.order.entity.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiaobai
 * @create 2021-08-19 17:23
 */
@Component
@FeignClient(name="service-edu",fallback =EduClientImpl.class )
public interface EduClient {

    //根据课程id查询课程的基本信息
    @GetMapping("/eduservice/course/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);

}
