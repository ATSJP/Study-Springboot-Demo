package com.demo.demoshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录测试Controller
 *
 * @author atsjp 2019/7/29
 */
@RestController
public class UserController {

  @GetMapping("/user/login")
  public String login(String userName, String password) {
    Subject currentUser = SecurityUtils.getSubject();
    if (currentUser.isAuthenticated()) {
      if (!userName.equals(currentUser.getPrincipal().toString())) {
        return "您已登录其他账号，请先退出后，在操作！";
      } else {
        return "您已登录，无需重复登录！";
      }
    } else {
      UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
      try {
        currentUser.login(usernamePasswordToken);
      } catch (LockedAccountException lae) {
        return "您的账号被锁定，请联系客服！";
      } catch (AuthenticationException uae) {
        return "用户名或密码不正确！";
      }
      if (!currentUser.isAuthenticated()) {
        return "系统异常！";
      }
    }
    return "<script>window.location.href='http://127.0.0.1/main'</script>";
  }

  @GetMapping("/user/test")
  public String test() {
    return "success";
  }

  @GetMapping("/index")
  public String index() {
    return "index";
  }

  @GetMapping("/main")
  public String main() {
    Subject currentUser = SecurityUtils.getSubject();
    String userName = currentUser.getPrincipal().toString();
    boolean hasUserRole = currentUser.hasRole("user");
    boolean hasAdminRole = currentUser.hasRole("admin");
    return "当前登录用户："
        + userName
        + "<br>是否具有user角色："
        + hasUserRole
        + "<br>是否具有admin角色："
        + hasAdminRole;
  }

  @GetMapping("/403")
  public String unauthorizedUrl() {
    return "403";
  }
}
