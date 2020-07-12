package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;

@SpringBootApplication
@EnableDubboConfiguration
public class DemoDubboRedisConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDubboRedisConsumerApplication.class, args);
	}

}
