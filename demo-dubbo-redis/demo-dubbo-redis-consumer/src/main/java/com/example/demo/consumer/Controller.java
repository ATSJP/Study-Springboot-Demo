package com.example.demo.consumer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.demo.DemoDubboRedisApi;

/** @author atsjp */
@RestController
public class Controller {

  @Reference(url = "dubbo://127.0.0.1:20880", check = false)
  private DemoDubboRedisApi demoDubboRedisApi;

  @GetMapping("/test")
  public String test(String name) {
    return demoDubboRedisApi.sayHello(name);
  }
}
