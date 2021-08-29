package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CacheRedisCaffeineService {

  private final Logger logger = LoggerFactory.getLogger(CacheRedisCaffeineService.class);

  @Cacheable(key = "'cache_user_id_' + #id", value = "userIdCache", cacheManager = "cacheManager")
  public Long get(long id) {
    logger.info("get by id from db");
    return 1L;
  }

  @Cacheable(key = "'cache_user_name_' + #name", value = "userNameCache",
      cacheManager = "cacheManager")
  public Long get(String name) {
    logger.info("get by name from db");
    return 1L;
  }

  @CachePut(key = "'cache_user_id_' + #userVO.id", value = "userIdCache",
      cacheManager = "cacheManager")
  public Long update(String userVO) {
    logger.info("update to db");
    return 1L;
  }

  @CacheEvict(key = "'cache_user_id_' + #id", value = "userIdCache", cacheManager = "cacheManager")
  public void delete(long id) {
    logger.info("delete from db");
  }

}
