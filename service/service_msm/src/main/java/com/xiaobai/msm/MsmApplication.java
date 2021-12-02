package com.xiaobai.msm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author xiaobai
 * @create 2021-08-09 9:54
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.xiaobai")
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class,args);
    }
}
