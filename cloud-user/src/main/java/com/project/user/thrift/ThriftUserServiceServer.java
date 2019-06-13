package com.project.user.thrift;

import com.project.thrift.entity.ThriftUserRoleVO;
import com.project.thrift.service.ThriftUserService;
import com.project.user.pojo.UserRoleVO;
import com.project.user.service.UserService;
import com.project.util.BeanUtils;
import io.ostenant.rpc.thrift.server.annotation.ThriftService;
import org.apache.thrift.TException;

import javax.annotation.Resource;

/**
 * 该类实现了XXXX相关操作接口的具体功能
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
    public ThriftUserRoleVO getByAccountName(String accountName) throws TException {
        UserRoleVO userRoleVO = userService.getByAccountName(accountName);
        if(userRoleVO != null){
            return BeanUtils.copyBean(userRoleVO,ThriftUserRoleVO.class);
        }
        return null;
    }
}
