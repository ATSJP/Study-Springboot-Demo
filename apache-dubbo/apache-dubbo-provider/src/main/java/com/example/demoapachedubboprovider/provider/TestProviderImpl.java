package com.example.demoapachedubboprovider.provider;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.protocol.dubbo.status.ThreadPoolStatusChecker;

import com.demo.dubbo.api.TestProvider;

/**
 * TestProviderImpl
 *
 * @author sjp
 * @date 2020/3/17
 */
@DubboService(version = "1.0.0")
public class TestProviderImpl implements TestProvider {
  @Override
  public String get(String a) {
    new ThreadPoolStatusChecker().check();
    return a + "cc";
  }
}
