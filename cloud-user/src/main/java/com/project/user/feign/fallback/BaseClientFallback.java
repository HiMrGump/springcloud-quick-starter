package com.project.user.feign.fallback;


import com.project.user.feign.BaseClient;
import com.project.util.ResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  该类定义了base服务feign降级处理
 *
 * @ClassName: BaseClientFallback
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 10:50
 * @Version: 1.0
 */
@Service
@Slf4j
public class BaseClientFallback implements FallbackFactory<BaseClient> {

    @Override
    public BaseClient create(Throwable throwable) {
        return new BaseClient() {
            @Override
            public ResponseResult getByType(String type) {
                return ResponseResult.serviceDown();
            }
        };
    }
}
