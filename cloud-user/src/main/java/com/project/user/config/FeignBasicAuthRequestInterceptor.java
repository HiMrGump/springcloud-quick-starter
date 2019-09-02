package com.project.user.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: FeignHeadConfiguration
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 18:21
 * @Version: 1.0
 */
public class FeignBasicAuthRequestInterceptor  implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String value = request.getHeader(name);
                    /**
                     * 遍历请求头里面的属性字段，将logId和token添加到新的请求头中转发到下游服务
                     * */
                    requestTemplate.header(name, value);

                }
            }
        }

    }
}
