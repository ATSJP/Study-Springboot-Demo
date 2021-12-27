package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication
@EnableDubboConfiguration
public class DemoDubboRedisProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoDubboRedisProviderApplication.class, args);
  }
}
