package com.example.cache.caffeine.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.MeterRegistry;
import io.prometheus.client.cache.caffeine.CacheMetricsCollector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }


    /**
     * 配置缓存管理器
     *
     * @return 缓存管理器
     */
    @Bean("caffeineCacheManager")
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder()
                // 初始的缓存空间大小
                // .initialCapacity(-1)
                // 缓存的最大条数
                // .maximumSize(-1)
                // 缓存的最大权重
                // .maximumWeight(-1)
                // 最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(Duration.ofMinutes(1))
                // 最后一次写入后经过固定时间过期
                // .expireAfterWrite(Duration.ofMinutes(1))
                // 创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
                // .refreshAfterWrite(Duration.ofMinutes(1))
                // .weakKeys()
                // .weakValues()
                .softValues()
                .recordStats();
        // 统计
        CacheMetricsCollector cacheMetricsCollector = new CacheMetricsCollector().register();
        cacheMetricsCollector.addCache("userInfo", caffeine.build());
        // 注意点：
        // expireAfterWrite和expireAfterAccess同事存在时，以expireAfterWrite为准
        // maximumSize和maximumWeight不可以同时使用
        cacheManager.setCaffeine(caffeine);

        return cacheManager;
    }


}
