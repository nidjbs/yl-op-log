package com.hyl.test.single.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author huayuanlin
 * @date 2021/08/09 19:52
 * @desc the class desc
 */
@SpringBootApplication
@MapperScan("com.hyl.test.single.demo.dao")
public class ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
