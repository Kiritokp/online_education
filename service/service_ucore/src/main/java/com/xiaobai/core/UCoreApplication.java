package com.xiaobai.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiaobai
 * @create 2021-08-10 13:27
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.xiaobai")
@MapperScan("com.xiaobai.core.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class UCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCoreApplication.class,args);
    }
}
