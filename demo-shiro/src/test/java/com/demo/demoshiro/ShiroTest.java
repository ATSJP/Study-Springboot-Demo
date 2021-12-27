package com.demo.demoshiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

/** @author sjp 2019/1/11 */
public class ShiroTest {

  @Test
  public void md5() {
    String userName = "test3";
    String password = "test";
    String hashAlgorithmName = "MD5";
    int hashIterations = 5;
    ByteSource credentialsSalt = ByteSource.Util.bytes(userName);
    Object obj = new SimpleHash(hashAlgorithmName, password, credentialsSalt, hashIterations);
    System.out.println(obj);
  }
}
