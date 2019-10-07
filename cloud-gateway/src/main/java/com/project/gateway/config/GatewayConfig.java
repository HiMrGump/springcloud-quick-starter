package com.project.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 该类定义了忽略url的列表
 *
 * @ClassName: GatewayConfig
 * @Author: WangQingYun
 * @Date: Created in 2019/4/29 11:25
 * @Version: 1.0
 */
public class GatewayConfig {
    private String ignoreUrls;
}
