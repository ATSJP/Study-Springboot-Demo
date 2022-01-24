package com.example.cache.jetcache.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Cached(name = "test", key = "#key", cacheType = CacheType.REMOTE, expire = 10)
    public String get(String key) {
        return key;
    }

}
