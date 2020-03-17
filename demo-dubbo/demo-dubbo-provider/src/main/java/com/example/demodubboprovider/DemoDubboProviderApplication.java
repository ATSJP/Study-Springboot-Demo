package com.example.demodubboprovider;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DemoDubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoDubboProviderApplication.class, args);
    }

}
