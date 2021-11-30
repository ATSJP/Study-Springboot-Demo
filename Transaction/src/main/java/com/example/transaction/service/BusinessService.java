package com.example.transaction.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class BusinessService {

    @Async
    public void doSlowBusiness() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            log.error("error", e);
        }
        log.info("1");
        throw new NullPointerException("exception");
    }

}
