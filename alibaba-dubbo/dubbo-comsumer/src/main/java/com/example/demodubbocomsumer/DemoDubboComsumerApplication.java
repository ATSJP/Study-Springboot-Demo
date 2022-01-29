package com.example.demodubbocomsumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class DemoDubboComsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoDubboComsumerApplication.class, args);
  }
}
