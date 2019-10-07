package com.project.user.feign;

import com.project.user.config.FeignConfig;
import com.project.user.feign.fallback.BaseClientFallback;
import com.project.util.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 该类定义了base服务feign调用接口
 *
 * @ClassName: DictClient
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 17:47
 * @Version: 1.0
 */
@FeignClient(name = "cloud-base", fallbackFactory = BaseClientFallback.class,configuration = FeignConfig.class)
@RequestMapping("/dict")
public interface BaseClient {
    /**
     * 获取字典
     * @param type 字典类型
     * @return
     */
    @GetMapping("/getByType/{type}")
    public ResponseResult getByType(@PathVariable("type") String type);
}
