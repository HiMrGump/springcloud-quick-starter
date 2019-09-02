package com.project.user.feign.fallback;


import com.project.user.feign.BaseClient;
import com.project.util.ResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: DictClientFallback
 * @Author: WangQingYun
 * @Date: Created in 2019/8/31 17:49
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
