package com.project.gateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * gateway全局网关，打印请求来源
 *
 * @ClassName: PrintLoggingGatewayFilterFactory
 * @Author: WangQingYun
 * @Date: Created in 2019/6/19 17:33
 * @Version: 1.0
 */
@Component
@Slf4j
public class PrintLoggingGatewayFilterFactory extends AbstractGatewayFilterFactory<PrintLoggingGatewayFilterFactory.Config> {

    public PrintLoggingGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
       return (exchange,chain) -> {
           if (!config.isEnabled()) {
               return chain.filter(exchange);
           }
           log.debug("find a request,Method:{} Host:{} Path:{} Query:{}", exchange.getRequest().getMethod().name(),  exchange.getRequest().getURI().getHost(),   exchange.getRequest().getURI().getPath(),   exchange.getRequest().getQueryParams());
           return chain.filter(exchange);
       };
    }

    @Data
    public static class Config {
        // 控制是否开启
        private boolean enabled;
        public Config() {}
    }

}
