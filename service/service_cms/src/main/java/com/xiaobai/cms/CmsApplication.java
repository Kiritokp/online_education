package com.xiaobai.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiaobai
 * @create 2021-08-03 16:15
 */
@SpringBootApplication
@EnableDiscoveryClient //nacos注册
@ComponentScan(basePackages = "com.xiaobai")
@MapperScan("com.xiaobai.cms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
