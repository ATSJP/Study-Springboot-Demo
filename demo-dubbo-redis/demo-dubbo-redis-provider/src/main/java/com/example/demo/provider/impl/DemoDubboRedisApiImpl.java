package com.example.demo.provider.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.demo.DemoDubboRedisApi;

/** @author atsjp */
@Service(interfaceClass = DemoDubboRedisApi.class)
@Component
public class DemoDubboRedisApiImpl implements DemoDubboRedisApi {

  @Override
  public String sayHello(String name) {
    return name + " hello";
  }
}
