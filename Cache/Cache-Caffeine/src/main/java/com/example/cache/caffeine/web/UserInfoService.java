package com.example.cache.caffeine.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

@Slf4j
@Service
@CacheConfig(cacheNames = "caffeineCacheManager")
public class UserInfoService {

    /**
     * 模拟数据库存储数据
     */
    private final HashMap<Integer, UserInfo> userInfoMap = new HashMap<>();

    @CachePut(cacheNames = "userInfo", key = "#userInfo.id")
    public UserInfo addUserInfo(UserInfo userInfo) {
        log.info("create:{}", userInfo);
        userInfoMap.put(userInfo.getId(), userInfo);
        return userInfo;
    }

    @Cacheable(cacheNames = "userInfo", key = "#id")
    public UserInfo getById(Integer id) {
        log.info("get:{}", id);
        return userInfoMap.get(id);
    }

    @CachePut(cacheNames = "userInfo", key = "#userInfo.id")
    public UserInfo updateUserInfo(UserInfo userInfo) {
        log.info("update:{}", userInfo);
        if (!userInfoMap.containsKey(userInfo.getId())) {
            return null;
        }
        UserInfo oldUserInfo = userInfoMap.get(userInfo.getId());
        oldUserInfo.setAge(userInfo.getAge());
        oldUserInfo.setName(userInfo.getName());
        oldUserInfo.setSex(userInfo.getSex());
        userInfoMap.put(oldUserInfo.getId(), oldUserInfo);
        return oldUserInfo;
    }

    @CacheEvict(cacheNames = "userInfo", key = "#id")
    public void deleteById(Integer id) {
        log.info("delete:{}", id);
        userInfoMap.remove(id);
    }

}
