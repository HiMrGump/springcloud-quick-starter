package com.project.auth.feign;

import com.project.auth.feign.fallback.UserClientFallback;
import com.project.util.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  user服务feign调用
 *
 * @ClassName: UserServiceClient
 * @Author: WangQingYun
 * @Date: Created in 2019/5/21 10:50
 * @Version: 1.0
 */
@FeignClient(name = "cloud-user", fallbackFactory = UserClientFallback.class)
@RequestMapping("/public")
public interface UserServiceClient {
    /**
     * 通过用户名查询用户、角色信息
     *
     * @param username 用户名
     * @return BaseUser
     */
    @GetMapping("/getUserByAccountName/{accountName}")
    ResponseResult findUserByUsername(@PathVariable("accountName") String username);
}
