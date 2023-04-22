package com.example.cacheredis;

import com.example.cacheredis.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CacheRedisApplicationTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedissonClient redissonClient;

    @Test
    void contextLoads() {
        final String key = "testUser";
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        redissonClient.getBucket(key).set(userDTO);
        RBucket<UserDTO> rBucket = redissonClient.getBucket(key);
        userDTO = rBucket.get();
        logger.info(userDTO.toString());
    }

}
