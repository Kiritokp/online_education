package com.xiaobai.acl.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobai
 * @create 2021-09-01 18:10
 */
public interface IndexService  {
    Map<String, Object> getUserInfo(String username);

    List<JSONObject> getMenu(String username);
}
