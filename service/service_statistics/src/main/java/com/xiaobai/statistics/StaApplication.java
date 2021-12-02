package com.xiaobai.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author xiaobai
 * @create 2021-08-23 22:40
 */
@SpringBootApplication
@MapperScan("com.xiaobai.statistics.mapper")
@ComponentScan("com.xiaobai")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling//开始定时任务
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
