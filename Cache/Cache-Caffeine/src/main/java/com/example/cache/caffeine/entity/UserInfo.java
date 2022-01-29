package com.example.cache.caffeine.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author atsjp
 */
@Data
@ToString
public class UserInfo {
  private Integer id;
  private String name;
  private String sex;
  private Integer age;
}
