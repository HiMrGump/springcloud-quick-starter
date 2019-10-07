package com.project.auth.thrift;

import com.project.thrift.service.ThriftUserService;
import io.ostenant.rpc.thrift.client.annotation.ThriftClient;
import io.ostenant.rpc.thrift.client.common.ThriftClientAware;

/**
 *  RPC调用用户模块
 *
 * @ClassName: ThriftUserServiceClient
 * @Author: WangQingYun
 * @Date: Created in 2019/6/12 20:30
 * @Version: 1.0
 */
@ThriftClient(serviceId = "user-service-thrift", refer = ThriftUserService.class)
public interface ThriftUserServiceClient extends ThriftClientAware<ThriftUserService.Client> {
}
