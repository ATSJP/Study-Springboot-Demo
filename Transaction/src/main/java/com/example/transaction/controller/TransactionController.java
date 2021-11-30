package com.example.transaction.controller;

import com.example.transaction.entity.User;
import com.example.transaction.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class TransactionController {

    @Resource
    private TransactionService transactionService;

    @GetMapping("/user/{id}")
    public User getById(@PathVariable Integer id) {
        return transactionService.getById(id);
    }

    @PutMapping("/user")
    public User add(@RequestBody User user) {
        return transactionService.add(user);
    }

}
