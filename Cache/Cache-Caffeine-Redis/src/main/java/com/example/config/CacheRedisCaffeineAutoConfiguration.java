package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @author fuwei.deng
 * @date 2018年1月26日 下午5:23:03
 * @version 1.0.0
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableConfigurationProperties(CacheRedisCaffeineProperties.class)
public class CacheRedisCaffeineAutoConfiguration {

  @Autowired private CacheRedisCaffeineProperties cacheRedisCaffeineProperties;

  @Bean
  @ConditionalOnBean(RedisTemplate.class)
  public RedisCaffeineCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
    return new RedisCaffeineCacheManager(cacheRedisCaffeineProperties, redisTemplate);
  }

  @Bean
  public RedisMessageListenerContainer redisMessageListenerContainer(
      RedisTemplate<Object, Object> redisTemplate,
      RedisCaffeineCacheManager redisCaffeineCacheManager) {
    RedisMessageListenerContainer redisMessageListenerContainer =
        new RedisMessageListenerContainer();
    redisMessageListenerContainer.setConnectionFactory(redisTemplate.getConnectionFactory());
    CacheMessageListener cacheMessageListener =
        new CacheMessageListener(redisTemplate, redisCaffeineCacheManager);
    redisMessageListenerContainer.addMessageListener(
        cacheMessageListener, new ChannelTopic(cacheRedisCaffeineProperties.getRedis().getTopic()));
    return redisMessageListenerContainer;
  }
}
