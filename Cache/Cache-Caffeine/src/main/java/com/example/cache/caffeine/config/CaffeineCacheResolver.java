package com.example.cache.caffeine.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.cache.CaffeineCacheMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;

import java.util.Collection;
import java.util.Set;

/**
 * Bind Caffeine Cache to MeterRegistry, instantiate the current bean and use
 * like @CacheConfig(cacheResolver = "caffeineCacheResolver")
 *
 * @author shijianpeng
 */
public class CaffeineCacheResolver extends SimpleCacheResolver {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  /** meterRegistry */
  private final MeterRegistry meterRegistry;
  /** CacheNames */
  private Set<String> cacheNames;

  public CaffeineCacheResolver(CacheManager cacheManager, MeterRegistry meterRegistry) {
    super(cacheManager);
    this.meterRegistry = meterRegistry;
  }

  @Override
  protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
    Set<String> cacheNames = context.getOperation().getCacheNames();
    CacheManager cacheManager = super.getCacheManager();
    cacheNames.forEach(
        cacheName -> {
          if (!this.cacheNames.contains(cacheName)) {
            this.bind(cacheManager, cacheName);
            this.cacheNames.add(cacheName);
          }
        });
    logger.info("cacheNames:{}", cacheNames);
    return cacheNames;
  }

  /**
   * bind Cache to Metrics
   *
   * @param cacheManager cacheManager
   * @param cacheName cacheName
   */
  private void bind(CacheManager cacheManager, String cacheName) {
    Cache cache = cacheManager.getCache(cacheName);
    if (cache != null) {
      Object nativeCache = cache.getNativeCache();
      if (nativeCache instanceof com.github.benmanes.caffeine.cache.Cache<?, ?>) {
        com.github.benmanes.caffeine.cache.Cache<?, ?> cacheNativeCache =
            (com.github.benmanes.caffeine.cache.Cache<?, ?>) cache.getNativeCache();
        CaffeineCacheMetrics.monitor(
            meterRegistry, cacheNativeCache, cache.getName(), Tags.empty());
      }
    }
  }
}
