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

  @GetMapping("/listByName")
  public List<User> listByName(String name) {
    return userService.getListByName(name);
  }

  @GetMapping("/listByNameAndAge")
  public List<User> listByNameAndAge(String name, Integer age) {
    return userService.getListByNameAndAge(name, age);
  }

  @GetMapping("/listOrderByAgeDesc")
  public List<User> listOrderByAgeDesc() {
    return userService.getListOrderByAgeDesc();
  }

  @GetMapping("/listOrderByAgeAsc")
  public List<User> listOrderByAgeAsc() {
    return userService.getListOrderByAgeAsc();
  }

  @GetMapping("/page")
  public Page<User> page(Integer pageNum, Integer pageSize) {
    return userService.getPage(pageNum, pageSize);
  }

  @GetMapping("/listGreaterThanAge")
  public List<User> listGreaterThanAge(Integer age) {
    return userService.getListGreaterThanAge(age);
  }

  @GetMapping("/listGreaterThanEqualAge")
  public List<User> listGreaterThanEqualAge(Integer age) {
    return userService.getListGreaterThanEqualAge(age);
  }

  @GetMapping("/listLessThanAge")
  public List<User> listLessThanAge(Integer age) {
    return userService.getListLessThanAge(age);
  }

  @GetMapping("/listLessThanEqualAge")
  public List<User> listLessThanEqualAge(Integer age) {
    return userService.getListLessThanEqualAge(age);
  }
}
