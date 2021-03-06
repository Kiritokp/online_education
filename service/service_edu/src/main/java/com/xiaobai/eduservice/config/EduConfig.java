package com.xiaobai.eduservice.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaobai
 * @create 2021-05-23 16:59
 */
@Configuration
@MapperScan("com.xiaobai.eduservice.mapper")
public class EduConfig {
    /**
     * 逻辑删除插件
     */
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }



    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
<<<<<<< HEAD
    //xiaobai test ~~~
//xiaobai test ~~~123
    //master test ~~~
=======
    //xiaobai  111
>>>>>>> xiaobai
}
