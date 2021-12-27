package com.example.demo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

  static {
    List<FlowRule> rules1 = new ArrayList<>();
    FlowRule rule1 = new FlowRule();
    rule1.setResource("HelloWorld1");
    rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
    rule1.setCount(1);
    rules1.add(rule1);
    FlowRuleManager.loadRules(rules1);

    List<FlowRule> rules2 = new ArrayList<>();
    FlowRule rule2 = new FlowRule();
    rule2.setResource("HelloWorld12");
    rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
    rule2.setCount(1);
    rules2.add(rule2);
    FlowRuleManager.loadRules(rules2);
  }

  @GetMapping("/hello1")
  public String hello1() {
    String result = "";
    try (Entry entry = SphU.entry("HelloWorld1")) {
      // 被保护的逻辑
      result = "hello1";
    } catch (BlockException ex) {
      // 处理被流控的逻辑
      result = "blocked1";
    }
    return result;
  }

  @SentinelResource(
      value = "HelloWorld2",
      blockHandler = "exception2Handler",
      fallback = "hello2Fallback")
  @GetMapping("/hello2")
  public String hello2() {
    int i = 1 / 0;
    return "hello2";
  }

  /** Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数 */
  public String hello2Fallback() {
    return "hello2Fallback";
  }

  /** Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致. */
  public String exception2Handler(BlockException ex) {
    ex.printStackTrace();
    return "hello2BlockException";
  }
}
