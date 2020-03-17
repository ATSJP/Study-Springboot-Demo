package com.example.demodubboprovider.provider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.dubbo.api.TestProvider;

/**
 * TestProviderImpl
 *
 * @author sjp
 * @date 2020/3/17
 */
@Service
public class TestProviderImpl implements TestProvider {
    @Override
    public String get(String a) {
        return a + "cc";
    }
}
