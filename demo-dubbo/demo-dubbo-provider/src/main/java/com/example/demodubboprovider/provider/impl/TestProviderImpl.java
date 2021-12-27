package com.example.demodubboprovider.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.dubbo.api.TestProvider;
import org.springframework.stereotype.Component;

/**
 * TestProviderImpl
 *
 * @author sjp
 * @date 2020/3/17
 */
@Service(interfaceClass = TestProvider.class)
@Component
public class TestProviderImpl implements TestProvider {
  @Override
  public String get(String a) {
    return a + "cc";
  }
}
