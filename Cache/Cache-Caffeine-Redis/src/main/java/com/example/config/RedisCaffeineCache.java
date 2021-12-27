package com.example.config;

import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fuwei.deng
 * @date 2018年1月26日 下午5:24:11
 * @version 1.0.0
 */
public class RedisCaffeineCache extends AbstractValueAdaptingCache {
  private final Logger logger = LoggerFactory.getLogger(RedisCaffeineCache.class);
  private String name;
  private RedisTemplate<Object, Object> redisTemplate;
  private Cache<Object, Object> caffeineCache;
  private String cachePrefix;
  private long defaultExpiration = 0;
  private Map<String, Long> expires;
  private String topic = "cache:redis:caffeine:topic";

  protected RedisCaffeineCache(boolean allowNullValues) {
    super(allowNullValues);
  }

  public RedisCaffeineCache(
      String name,
      RedisTemplate<Object, Object> redisTemplate,
      Cache<Object, Object> caffeineCache,
      CacheRedisCaffeineProperties cacheRedisCaffeineProperties) {
    super(cacheRedisCaffeineProperties.isCacheNullValues());
    this.name = name;
    this.redisTemplate = redisTemplate;
    this.caffeineCache = caffeineCache;
    this.cachePrefix = cacheRedisCaffeineProperties.getCachePrefix();
    this.defaultExpiration = cacheRedisCaffeineProperties.getRedis().getDefaultExpiration();
    this.expires = cacheRedisCaffeineProperties.getRedis().getExpires();
    this.topic = cacheRedisCaffeineProperties.getRedis().getTopic();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Object getNativeCache() {
    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T get(Object key, Callable<T> valueLoader) {
    Object value = lookup(key);
    if (value != null) {
      return (T) value;
    }

    ReentrantLock lock = new ReentrantLock();
    try {
      lock.lock();
      value = lookup(key);
      if (value != null) {
        return (T) value;
      }
      value = valueLoader.call();
      Object storeValue = toStoreValue(valueLoader.call());
      put(key, storeValue);
      return (T) value;
    } catch (Exception e) {
      try {
        Class<?> c = Class.forName("org.springframework.cache.Cache$ValueRetrievalException");
        Constructor<?> constructor =
            c.getConstructor(Object.class, Callable.class, Throwable.class);
        RuntimeException exception =
            (RuntimeException) constructor.newInstance(key, valueLoader, e.getCause());
        throw exception;
      } catch (Exception e1) {
        throw new IllegalStateException(e1);
      }
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void put(Object key, Object value) {
    if (!super.isAllowNullValues() && value == null) {
      this.evict(key);
      return;
    }
    long expire = getExpire();
    if (expire > 0) {
      redisTemplate
          .opsForValue()
          .set(getKey(key), toStoreValue(value), expire, TimeUnit.MILLISECONDS);
    } else {
      redisTemplate.opsForValue().set(getKey(key), toStoreValue(value));
    }

    push(new CacheMessage(this.name, key));

    caffeineCache.put(key, value);
  }

  @Override
  public ValueWrapper putIfAbsent(Object key, Object value) {
    Object cacheKey = getKey(key);
    Object prevValue = null;
    // 考虑使用分布式锁，或者将redis的setIfAbsent改为原子性操作
    synchronized (key) {
      prevValue = redisTemplate.opsForValue().get(cacheKey);
      if (prevValue == null) {
        long expire = getExpire();
        if (expire > 0) {
          redisTemplate
              .opsForValue()
              .set(getKey(key), toStoreValue(value), expire, TimeUnit.MILLISECONDS);
        } else {
          redisTemplate.opsForValue().set(getKey(key), toStoreValue(value));
        }

        push(new CacheMessage(this.name, key));

        caffeineCache.put(key, toStoreValue(value));
      }
    }
    return toValueWrapper(prevValue);
  }

  @Override
  public void evict(Object key) {
    // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
    redisTemplate.delete(getKey(key));

    push(new CacheMessage(this.name, key));

    caffeineCache.invalidate(key);
  }

  @Override
  public void clear() {
    // 先清除redis中缓存数据，然后清除caffeine中的缓存，避免短时间内如果先清除caffeine缓存后其他请求会再从redis里加载到caffeine中
    Set<Object> keys = redisTemplate.keys(this.name.concat(":"));
    for (Object key : keys) {
      redisTemplate.delete(key);
    }

    push(new CacheMessage(this.name, null));

    caffeineCache.invalidateAll();
  }

  @Override
  protected Object lookup(Object key) {
    Object cacheKey = getKey(key);
    Object value = caffeineCache.getIfPresent(key);
    if (value != null) {
      logger.debug("get cache from caffeine, the key is : {}", cacheKey);
      return value;
    }

    value = redisTemplate.opsForValue().get(cacheKey);

    if (value != null) {
      logger.debug("get cache from redis and put in caffeine, the key is : {}", cacheKey);
      caffeineCache.put(key, value);
    }
    return value;
  }

  private Object getKey(Object key) {
    return this.name
        .concat(":")
        .concat(
            StringUtils.isEmpty(cachePrefix)
                ? key.toString()
                : cachePrefix.concat(":").concat(key.toString()));
  }

  private long getExpire() {
    long expire = defaultExpiration;
    Long cacheNameExpire = expires.get(this.name);
    return cacheNameExpire == null ? expire : cacheNameExpire.longValue();
  }

  /**
   * @description 缓存变更时通知其他节点清理本地缓存
   * @author fuwei.deng
   * @date 2018年1月31日 下午3:20:28
   * @version 1.0.0
   * @param message
   */
  private void push(CacheMessage message) {
    redisTemplate.convertAndSend(topic, message);
  }

  /**
   * @description 清理本地缓存
   * @author fuwei.deng
   * @date 2018年1月31日 下午3:15:39
   * @version 1.0.0
   * @param key
   */
  public void clearLocal(Object key) {
    logger.debug("clear local cache, the key is : {}", key);
    if (key == null) {
      caffeineCache.invalidateAll();
    } else {
      caffeineCache.invalidate(key);
    }
  }
}
