package com.demo.demoshiro.tools;

import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代替数据库查询，密码是md5的5次盐值加密，具体参考com.demo.demoshiro.ShiroTest#md5()
 *
 * @author atsjp 2019/7/26
 */
public class UserTools {

  private String userName;

  private String userPassword;

  private static List<UserTools> userToolsList = new LinkedList<>();

  private UserTools(String userName, String userPassword) {
    this.userName = userName;
    this.userPassword = userPassword;
  }

  static {
    userToolsList.add(new UserTools("test1", "414216d81f6e99629e25a9efd8122892"));
    userToolsList.add(new UserTools("test2", "97f2e1357bb4c6011682d54b9e6ed024"));
    userToolsList.add(new UserTools("test3", "27165b884aee6f63a0b5f04e3e38445a"));
    userToolsList.add(new UserTools("test4", "b668737143a338a236ed5fb77e86b486"));
    userToolsList.add(new UserTools("monster", "1d86707cf7610819d1e62e3c5b622275"));
    userToolsList.add(new UserTools("admin", "4b252ef32f83fdec9ce52366a161dbc0"));
  }

  public static UserTools getUserByName(String userName) {
    List<UserTools> tempUserToolsList =
        userToolsList.stream()
            .filter(item -> userName.equals(item.getUserName()))
            .collect(Collectors.toList());
    return CollectionUtils.isEmpty(tempUserToolsList) ? null : tempUserToolsList.get(0);
  }

  public String getUserName() {
    return userName;
  }

  public String getUserPassword() {
    return userPassword;
  }
}
