package com.example.demodubboprovider;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** @author atsjp */
@SpringBootApplication
@EnableDubboConfiguration
public class DemoDubboProviderApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoDubboProviderApplication.class, args);
  }
}
