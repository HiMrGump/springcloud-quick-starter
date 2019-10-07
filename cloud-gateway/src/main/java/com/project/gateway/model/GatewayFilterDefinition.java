package com.project.gateway.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 路由过滤器定义
 *
 * @ClassName: GatewayFilterDefinition
 * @Author: WangQingYun
 * @Date: Created in 2019/6/18 9:35
 * @Version: 1.0
 */
@Data
public class GatewayFilterDefinition {
    // Filter Name
    private String name;

    // 对应的路由规则
    private Map<String, String> args = new LinkedHashMap<>();
}
