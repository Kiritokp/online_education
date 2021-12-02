package com.xiaobai.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiaobai
 * @create 2021-05-23 16:58
 */
@SpringBootApplication
@EnableDiscoveryClient  //Nacos注册
@EnableFeignClients  //服务调用
@ComponentScan(basePackages = {"com.xiaobai"})
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
