package com.project.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * 动态路由服务
 */
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    // 增加路由
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    // 更新路由
    public String update(RouteDefinition definition) {
        try {
            delete(definition.getId());
        } catch (Exception e) {
            return "update fail, not find route routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }
    }

    // 删除路由
    public Mono<ResponseEntity<Object>> delete(String id) {
        return this.routeDefinitionWriter.delete(Mono.just(id)).then(Mono.defer(() -> {
            return Mono.just(ResponseEntity.ok().build());
        })).onErrorResume((t) -> {
            return t instanceof NotFoundException;
        }, (t) -> {
            return Mono.just(ResponseEntity.notFound().build());
        });
    }

    // 清空路由表
    public String clear() {
        //routeDefinitionWriter.delete(Mono.f);
        //this.publisher.publishEvent(new RefreshRoutesEvent(this));
//        Mono<String> data;
//        Flux<RouteDefinition> routeList = routeDefinitionLocator.getRouteDefinitions();
//        routeList.reduce(data, (list, item) -> {
//            return data;
//        });
        return "success";
    }

}
