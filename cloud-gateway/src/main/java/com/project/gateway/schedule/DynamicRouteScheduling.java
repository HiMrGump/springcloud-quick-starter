package com.project.gateway.schedule;

import com.alibaba.fastjson.JSON;
import com.project.gateway.model.GatewayFilterDefinition;
import com.project.gateway.model.GatewayPredicateDefinition;
import com.project.gateway.model.GatewayRouteDefinition;
import com.project.gateway.service.DynamicRouteService;
import com.project.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: DynamicRouteScheduling
 * @Author: WangQingYun
 * @Date: Created in 2019/6/19 15:13
 * @Version: 1.0
 */
@Component
public class DynamicRouteScheduling {

    @Autowired
    private DynamicRouteService dynamicRouteService; // 动态路由实现类

   // public String resultRoutes = "[{\"id\":\"cloud-auth\",\"order\":0,\"uri\":\"lb://cloud-auth\",\"predicates\":[{\"name\":\"Path\",\"args\":{\"pattern\":\"/auth-proxy/**\"}}],\"filters\":[{\"args\":{\"enable\":true},\"name\":\"PrintLogging\"},{\"args\":{\"parts\":1},\"name\":\"StripPrefix\"}]},{\"id\":\"cloud-user\",\"order\":1,\"uri\":\"lb://cloud-auth\",\"predicates\":[{\"name\":\"Path\",\"args\":{\"pattern\":\"/user-proxy/**\"}}],\"filters\":[{\"args\":{\"parts\":1},\"name\":\"StripPrefix\"}]}]";
    public String resultRoutes = "[{\"id\":\"cloud-test\",\"order\":0,\"uri\":\"http://www.baidu.com\",\"predicates\":[{\"name\":\"Path\",\"args\":{\"pattern\":\"/aa/**\"}}],\"filters\":[{\"args\":{\"enabled\":true},\"name\":\"PrintLogging\"},{\"args\":{\"parts\":1},\"name\":\"StripPrefix\"}]},{\"id\":\"cloud-auth\",\"order\":1,\"uri\":\"lb://cloud-auth\",\"predicates\":[{\"name\":\"Path\",\"args\":{\"pattern\":\"/auth-proxy/**\"}}],\"filters\":[{\"args\":{\"enabled\":true},\"name\":\"PrintLogging\"},{\"args\":{\"parts\":1},\"name\":\"StripPrefix\"}]},{\"id\":\"cloud-user\",\"order\":1,\"uri\":\"lb://cloud-user\",\"predicates\":[{\"name\":\"Path\",\"args\":{\"pattern\":\"/user-proxy/**\"}}],\"filters\":[{\"args\":{\"enabled\":true},\"name\":\"PrintLogging\"},{\"args\":{\"parts\":1},\"name\":\"StripPrefix\"}]},{\"id\":\"cloud-base\",\"order\":1,\"uri\":\"lb://cloud-base\",\"predicates\":[{\"name\":\"Path\",\"args\":{\"pattern\":\"/base-proxy/**\"}}],\"filters\":[{\"args\":{\"enabled\":true},\"name\":\"PrintLogging\"},{\"args\":{\"parts\":1},\"name\":\"StripPrefix\"}]}]";


    // 每60秒中执行一次
    // 如果版本号不相等则获取最新路由信息并更新网关路由
    @Scheduled(cron = "*/60 * * * * ?")
    public void getDynamicRouteInfo() {
        try {
            System.out.println("从redis动态获取路由: " + DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
                if (!StringUtils.isEmpty(resultRoutes)) {
                    // 这里不能做清空原有路由的动作，变化中删除的路由需要通过指定ID来删除
                    List<GatewayRouteDefinition> list = JSON.parseArray(resultRoutes, GatewayRouteDefinition.class);
                    for (GatewayRouteDefinition definition : list) {
                        // 更新路由
                        RouteDefinition routeDefinition = assembleRouteDefinition(definition);
                        dynamicRouteService.update(routeDefinition);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 把前端传递的参数转换成路由对象
    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwDefinition) {
        RouteDefinition definition = new RouteDefinition();
        definition.setId(gwDefinition.getId());
        definition.setOrder(gwDefinition.getOrder());

        // 设置断言
        List<PredicateDefinition> pdList = new ArrayList<>();
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = gwDefinition.getPredicates();
        for (GatewayPredicateDefinition gpDefinition : gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);

        // 设置过滤器
        List<FilterDefinition> filters = new ArrayList();
        List<GatewayFilterDefinition> gatewayFilters = gwDefinition.getFilters();
        for (GatewayFilterDefinition filterDefinition : gatewayFilters){
            FilterDefinition filter = new FilterDefinition();
            filter.setName(filterDefinition.getName());
            filter.setArgs(filterDefinition.getArgs());
            filters.add(filter);
        }
        definition.setFilters(filters);

        URI uri = null;
        if (gwDefinition.getUri().startsWith("http")) {
            uri = UriComponentsBuilder.fromHttpUrl(gwDefinition.getUri()).build().toUri();
        } else {
            uri = URI.create(gwDefinition.getUri());
        }
        definition.setUri(uri);

        return definition;
    }
}
