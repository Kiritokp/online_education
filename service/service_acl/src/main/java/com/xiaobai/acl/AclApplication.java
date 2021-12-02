package com.xiaobai.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiaobai
 * @create 2021-08-31 9:32
 */
@MapperScan("com.xiaobai.acl.mapper")
@ComponentScan("com.xiaobai")
@SpringBootApplication
@EnableDiscoveryClient
public class AclApplication {
    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class,args);
    }
}
