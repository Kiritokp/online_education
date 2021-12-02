package com.xiaobai.order.client;

import com.xiaobai.commonutils.R;
import com.xiaobai.order.entity.CourseWebVoOrder;
import org.springframework.stereotype.Component;

/**
 * @author xiaobai
 * @create 2021-08-19 17:24
 */
@Component
public class EduClientImpl implements EduClient{
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String id) {
        System.out.println("服务降级了~~~");
        return null;
    }
}
