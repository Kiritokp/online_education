package com.xiaobai.msm.service;

import java.util.HashMap;

/**
 * @author xiaobai
 * @create 2021-08-09 9:56
 */
public interface MsmService {
    boolean send(HashMap<String, Object> param, String phone);
}
