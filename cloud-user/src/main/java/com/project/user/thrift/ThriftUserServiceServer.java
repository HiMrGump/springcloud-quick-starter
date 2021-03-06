package com.project.user.thrift;

import com.project.thrift.entity.ThriftResponseResult;
import com.project.thrift.service.ThriftUserService;
import com.project.thrift.util.ThriftUtils;
import com.project.user.pojo.UserRoleVO;
import com.project.user.service.UserService;
import io.ostenant.rpc.thrift.server.annotation.ThriftService;
import org.apache.thrift.TException;

import javax.annotation.Resource;

/**
 * 该类实现了RPC调用user-service相关操作
 *
 * @ClassName: ThriftUserServiceImpl
 * @Author: WangQingYun
 * @Date: Created in 2019/6/12 17:48
 * @Version: 1.0
 */
@ThriftService(name = "thriftUserService", version = 1.0)
public class ThriftUserServiceServer implements ThriftUserService.Iface{

    @Resource
    private UserService userService;

    @Override
    public ThriftResponseResult getByAccountName(String accountName) throws TException {
        UserRoleVO userRoleVO = userService.getByAccountName(accountName);
        return ThriftUtils.success(userRoleVO);
    }

    @Override
    public ThriftResponseResult getByMobile(String mobile) throws TException {
        UserRoleVO userRoleVO = userService.getByMobile(mobile);
        return ThriftUtils.success(userRoleVO);
    }
}
