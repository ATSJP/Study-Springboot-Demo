package com.example.demo.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author shijianpeng
 */
@RestController
public class TestController {

  @GetMapping("/hello3")
  @HystrixCommand(groupKey = "hello3GroupKey", commandKey = "hello3CommandKey",
      threadPoolKey = "hello3ThreadPoolKey", fallbackMethod = "defaultHello3",
      commandProperties = {@HystrixProperty(
          name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")},
      threadPoolProperties = {@HystrixProperty(name = "coreSize", value = "10"),
          @HystrixProperty(name = "maxQueueSize", value = "1000"),
          @HystrixProperty(name = "queueSizeRejectionThreshold", value = "100")},
      defaultFallback = "defaultHello3")
  public Object hello3() {
    // 模拟业务操作耗时
    int random = (int) ((Math.random() * (2000 - 50)) + 50);
    System.out.println(random);
    try {
      TimeUnit.MILLISECONDS.sleep(random);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "suc";
  }

  @GetMapping("/hello2")
  @HystrixCommand(fallbackMethod = "defaultHello2", commandProperties = {
      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")})
  public Object hello2(Integer time) {
    // 模拟业务操作耗时
    try {
      TimeUnit.MILLISECONDS.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "suc";
  }

  @GetMapping("/hello1")
  @HystrixCommand(fallbackMethod = "defaultHello1")
  public Object hello1() {
    // 模拟业务操作耗时
    int random = (int) ((Math.random() * (2000 - 50)) + 50);
    System.out.println(random);
    try {
      TimeUnit.MILLISECONDS.sleep(random);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "suc";
  }

  public Object defaultHello1() {
    return "limit1";
  }

  public Object defaultHello2(Integer time) {
    return "limit2";
  }

  public Object defaultHello3() {
    return "limit2";
  }

}
