package com.example.demodubboprovider.controller;

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

    @GetMapping("/test")
    public String test(){
        return "123";
    }
}
