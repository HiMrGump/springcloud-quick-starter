package com.project.auth.thrift;

import com.project.thrift.service.ThriftUserService;
import io.ostenant.rpc.thrift.client.annotation.ThriftClient;
import io.ostenant.rpc.thrift.client.common.ThriftClientAware;

/**
 * 该类实现了XXXX相关操作接口的具体功能
 *
 * @ClassName: ThriftUserServiceClient
 * @Author: WangQingYun
 * @Date: Created in 2019/6/12 20:30
 * @Version: 1.0
 */
@ThriftClient(serviceId = "user-service-thrift", refer = ThriftUserService.class)
public interface ThriftUserServiceClient extends ThriftClientAware<ThriftUserService.Client> {
}
