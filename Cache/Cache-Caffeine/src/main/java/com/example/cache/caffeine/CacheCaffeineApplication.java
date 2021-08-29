package com.example.cache.caffeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class CacheCaffeineApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacheCaffeineApplication.class, args);
    }

}
