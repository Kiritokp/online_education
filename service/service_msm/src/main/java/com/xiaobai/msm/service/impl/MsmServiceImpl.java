package com.xiaobai.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiaobai.msm.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
/**
 * @author xiaobai
 * @create 2021-08-09 9:56
 */
@Service
public class MsmServiceImpl implements MsmService {
    //发送短信
    @Override
    public boolean send(HashMap<String, Object> param, String phone) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAI5tCPKTrCQkqQXNiVrRRR", "tZsWwZuSQ5KiOTS96dP1XiptxhXv32");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "阿月的小窝");
        request.putQueryParameter("TemplateCode", "SMS_221731021");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;
    }
}
