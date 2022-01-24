package com.example.cache.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodCache(basePackages = "com.example.cache.jetcache")
@EnableCreateCacheAnnotation
public class CacheJetCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheJetCacheApplication.class, args);
    }

}
