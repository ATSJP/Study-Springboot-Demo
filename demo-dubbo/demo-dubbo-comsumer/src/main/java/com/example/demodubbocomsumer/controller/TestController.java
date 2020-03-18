package com.example.demodubbocomsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.demo.dubbo.api.TestProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author sjp
 * @date 2020/3/17
 */
@RestController
public class TestController {

    @Reference(url = "dubbo://127.0.0.1:20880")
    private TestProvider testProvider;

    @GetMapping("/test")
    public String test(String a) {
        return testProvider.get(a);
    }
}
