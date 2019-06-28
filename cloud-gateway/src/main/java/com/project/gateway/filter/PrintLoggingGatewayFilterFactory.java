package com.project.gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: LoggingGatewayFilterFactory
 * @Author: WangQingYun
 * @Date: Created in 2019/6/19 17:33
 * @Version: 1.0
 */
@Component
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

            String info = String.format("Method:{%s} Host:{%s} Path:{%s} Query:{%s}",
            exchange.getRequest().getMethod().name(),
            exchange.getRequest().getURI().getHost(),
            exchange.getRequest().getURI().getPath(),
            exchange.getRequest().getQueryParams());
           System.out.println(info);
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
