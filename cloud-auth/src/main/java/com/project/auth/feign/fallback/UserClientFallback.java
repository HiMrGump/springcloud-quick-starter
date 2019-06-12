package com.project.auth.feign.fallback;

import com.project.auth.feign.UserServiceClient;
import com.project.util.ResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author TerryYu
 * @date 2019-04-10
 * 用户服务的fallback
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
