package com.example.cache.caffeine.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author shijianpeng
 */
@Configuration
public class CacheConfig {

  @Bean
  MeterRegistryCustomizer<MeterRegistry> configurer(
      @Value("${spring.application.name}") String applicationName) {
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
    /// Caffeine caffeine = Caffeine.newBuilder()
    // // 初始的缓存空间大小
    // // .initialCapacity(-1)
    // // 缓存的最大条数
    // // .maximumSize(-1)
    // // 缓存的最大权重
    // // .maximumWeight(-1)
    // // 最后一次写入或访问后经过固定时间过期
    // .expireAfterAccess(Duration.ofSeconds(10))
    // // 最后一次写入后经过固定时间过期
    // // .expireAfterWrite(Duration.ofMinutes(1))
    // // 创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
    // // .refreshAfterWrite(Duration.ofMinutes(1))
    // // .weakKeys()
    // // .weakValues()
    // // 将value设置为软引用
    // .softValues().recordStats();
    // 注意点：
    // expireAfterWrite和expireAfterAccess同事存在时，以expireAfterWrite为准
    // maximumSize和maximumWeight不可以同时使用
    cacheManager.setCacheSpecification(
        "initialCapacity=50,maximumSize=500,expireAfterWrite=1h,recordStats");
    cacheManager.setCacheNames(Collections.singletonList("userInfo"));
    return cacheManager;
  }

  @Bean
  public List<MeterBinder> getMeterBinder(CaffeineCacheManager caffeineCacheManager) {
    List<MeterBinder> meterBinderList = new ArrayList<>();
    Collection<String> cacheNames = caffeineCacheManager.getCacheNames();
    cacheNames.forEach(cacheName -> {
      Cache cache = caffeineCacheManager.getCache(cacheName);
      if (cache != null) {
        MeterBinder meterBinder = new CaffeineCacheMetrics(
            (com.github.benmanes.caffeine.cache.Cache<?, ?>) cache.getNativeCache(),
            cache.getName(), Tags.of(cache.getName(), cache.getName()));
        meterBinderList.add(meterBinder);
      }
    });
    return meterBinderList;
  }

  /// CacheMetricsCollector cacheMetrics = new CacheMetricsCollector().register();
  // Cache<String, String> cache = Caffeine.newBuilder().recordStats().build();
  // cacheMetrics.addCache("myCacheLabel", cache);

}
