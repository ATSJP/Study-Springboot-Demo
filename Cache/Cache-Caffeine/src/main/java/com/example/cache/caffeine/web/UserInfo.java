package com.example.cache.caffeine.web;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfo {
  private Integer id;
  private String name;
  private String sex;
  private Integer age;
}
