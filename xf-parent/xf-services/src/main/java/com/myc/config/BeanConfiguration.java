package com.myc.config;

import com.ardsec.framework.mybatis.id.IdGenerator;
import com.ardsec.framework.mybatis.id.impl.DefaultIdGenerator;
import com.ardsec.framework.mybatis.plgins.MySQLPagingInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 */
@Configuration
public class BeanConfiguration {

    /**
     * mysql分页拦截器
     * @return
     */
    @Bean
    public Interceptor paginationInterceptor(){
        return new MySQLPagingInterceptor();
    }

    /**
     * Id主键生成器
     * @return
     */
    @Bean
    public IdGenerator idGenerator() {
        return new DefaultIdGenerator();
    }
}
