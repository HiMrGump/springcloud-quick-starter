package com.project.gateway.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由断言定义
 *
 * @ClassName: GatewayPredicateDefinition
 * @Author: WangQingYun
 * @Date: Created in 2019/6/18 9:35
 * @Version: 1.0
 */
@Data
public class GatewayPredicateDefinition {
    // 断言对应的Name
    private String name;

    // 配置的断言规则
    private Map<String, String> args = new LinkedHashMap<>();
}
