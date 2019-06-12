package com.project.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: GatewayConfig
 * @Author: WangQingYun
 * @Date: Created in 2019/4/29 11:25
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "lvpai-cloud-gateway") // 前缀：对应consul 配置中心的 ignoreUrl 前缀
public class GatewayConfig {
    private String ignoreUrls;
}
