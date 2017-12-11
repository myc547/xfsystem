package com.myc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author myc
 * @version 1.0, 2017/12/7
 */
@SpringBootApplication
@MapperScan("com.myc.xf.dao.mappers")
public class XfServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XfServiceApplication.class, args);
    }
}
