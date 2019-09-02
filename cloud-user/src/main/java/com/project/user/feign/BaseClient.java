package com.project.user.feign;

import com.project.user.config.FeignConfig;
import com.project.user.feign.fallback.BaseClientFallback;
import com.project.util.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: DictClient
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 17:47
 * @Version: 1.0
 */
@FeignClient(name = "cloud-base", fallbackFactory = BaseClientFallback.class,configuration = FeignConfig.class)
@RequestMapping("/dict")
public interface BaseClient {

    @GetMapping("/getByType/{type}")
    public ResponseResult getByType(@PathVariable("type") String type);
}
