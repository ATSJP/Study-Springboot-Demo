package com.example.cache.jetcache.controller;

import com.example.cache.jetcache.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
public class Controller {

    @Resource
    private CacheService cacheService;


    @GetMapping(value = "/get/{key}")
    public String get(@PathVariable String key) {
        return cacheService.get(key);
    }

}
