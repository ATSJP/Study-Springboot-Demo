package com.example.es.controller;

import com.example.es.entity.User;
import com.example.es.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author atsjp
 * @date 2022/4/23 17:43
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findByName")
    public List<User> findByName(String name) {
        return userService.findByName(name);
    }

    @GetMapping("/findByNameContaining")
    public List<User> findByNameContaining(String name) {
        return userService.findByNameContaining(name);
    }

    @GetMapping("/findByNameAndAge")
    public List<User> findByNameAndAge(String name, Integer age) {
        return userService.findByNameAndAge(name, age);
    }

    @GetMapping("/findByOrderByAgeDesc")
    public List<User> findByOrderByAgeDesc() {
        return userService.findByOrderByAgeDesc();
    }

    @GetMapping("/findByOrderByAgeAsc")
    public List<User> findByOrderByAgeAsc() {
        return userService.findByOrderByAgeAsc();
    }

    @GetMapping("/findByAgeGreaterThan")
    public List<User> findByAgeGreaterThan(Integer age) {
        return userService.findByAgeGreaterThan(age);
    }

    @GetMapping("/findByAgeGreaterThanEqual")
    public List<User> findByAgeGreaterThanEqual(Integer age) {
        return userService.findByAgeGreaterThanEqual(age);
    }

    @GetMapping("/findByAgeLessThan")
    public List<User> findByAgeLessThan(Integer age) {
        return userService.findByAgeLessThan(age);
    }

    @GetMapping("/findByAgeLessThanEqual")
    public List<User> findByAgeLessThanEqual(Integer age) {
        return userService.findByAgeLessThanEqual(age);
    }

    @GetMapping("/findByAddressContaining")
    public List<User> findByAddressContaining(String address) {
        return userService.findByAddressContaining(address);
    }

    @GetMapping("/search")
    public Page<User> search(User user) {
        return userService.search(user);
    }

    @PutMapping
    public long save(@RequestBody User user) {
        long id = System.currentTimeMillis();
        user.setId(id);
        userService.save(user);
        return id;
    }

    @PostMapping
    public String update(@RequestBody User user) {
        userService.save(user);
        return "success";
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "success";
    }

    @GetMapping("/all")
    public List<User> all() {
        return userService.getAll();
    }

    @GetMapping("/page")
    public Page<User> page(Integer pageNum, Integer pageSize) {
        return userService.getPage(pageNum, pageSize);
    }
}
