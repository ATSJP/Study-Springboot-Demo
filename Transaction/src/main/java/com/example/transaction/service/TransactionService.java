package com.example.transaction.service;

import com.example.transaction.dao.UserDao;
import com.example.transaction.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

@Slf4j
@Service
public class TransactionService {

    @Resource
    private BusinessService businessService;
    @Resource
    private UserDao userDao;

    public User getById(Integer id) {
        return userDao.findById(id).orElse(new User());
    }

    @Transactional
    public User add(User user) {
        doOtherBusiness();
        User result = userDao.save(user);
        log.info("2,user:{},result:{}", user, result);
        return result;
    }

    private void doOtherBusiness() {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            // 注册事务同步处理
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    businessService.doSlowBusiness();
                }
            });
        }
    }

}