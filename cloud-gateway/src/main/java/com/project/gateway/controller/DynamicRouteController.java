package com.project.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * 获取动态路由接口
 *
 * @ClassName: DynamicRouteController
 * @Author: WangQingYun
 * @Date: Created in 2019/9/2 17:23
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dynamicRoute")
public class DynamicRouteController {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    // 获取网关所有的路由信息
    @RequestMapping("/routes")
    public Flux<RouteDefinition> getRouteDefinitions() {
        return routeDefinitionLocator.getRouteDefinitions();
    }
}
