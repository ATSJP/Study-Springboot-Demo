package com.example.demoapachedubboconsumer;

import com.demo.dubbo.api.TestProvider;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class DemoApacheDubboConsumerApplication {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
  private TestProvider testProvider;

  public static void main(String[] args) {
    SpringApplication.run(DemoApacheDubboConsumerApplication.class).close();
  }

  @Bean
  public ApplicationRunner runner() {
    return args -> {
      logger.info("========================================= " + testProvider.get("test"));
    };
  }
}
