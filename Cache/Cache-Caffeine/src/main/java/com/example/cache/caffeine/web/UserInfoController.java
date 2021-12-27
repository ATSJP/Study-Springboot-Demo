package com.example.cache.caffeine.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/** @author atsjp */
@CrossOrigin
@RestController
@RequestMapping
public class UserInfoController {

  @Resource private UserInfoService userInfoService;

  @GetMapping("/userInfo/{id}")
  public Object getUserInfo(@PathVariable Integer id) {
    UserInfo userInfo = userInfoService.getById(id);
    if (userInfo == null) {
      return "没有该用户";
    }
    return userInfo;
  }

  @PostMapping("/userInfo")
  public Object createUserInfo(@RequestBody UserInfo userInfo) {
    return userInfoService.addUserInfo(userInfo);
  }

  @PutMapping("/userInfo")
  public Object updateUserInfo(@RequestBody UserInfo userInfo) {
    UserInfo newUserInfo = userInfoService.updateUserInfo(userInfo);
    if (newUserInfo == null) {
      return "不存在该用户";
    }
    return newUserInfo;
  }

  @DeleteMapping("/userInfo/{id}")
  public Object deleteUserInfo(@PathVariable Integer id) {
    userInfoService.deleteById(id);
    return "SUCCESS";
  }
}
