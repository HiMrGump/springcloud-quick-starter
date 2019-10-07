package com.project.auth.feign.fallback;

import com.project.auth.feign.UserServiceClient;
import com.project.util.ResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  user服务feign降级处理
 *
 * @ClassName: UserClientFallback
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 10:50
 * @Version: 1.0
 */
@Service
@Slf4j
public class UserClientFallback implements FallbackFactory<UserServiceClient> {

    @Override
    public UserServiceClient create(Throwable throwable) {
        return new UserServiceClient(){

            @Override
            public ResponseResult findUserByUsername(String username) {
                return ResponseResult.serviceDown();
            }
        };
    }
}
