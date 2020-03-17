package com.example.demodubbocomsumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DemoDubboComsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDubboComsumerApplication.class, args);
    }

}
