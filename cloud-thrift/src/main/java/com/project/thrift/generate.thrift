// 命令 thrift -gen java ./generate.thrift
namespace java com.project.thrift

// 分页控制参数
struct ThriftPageHelper {
  1: required i32 currentPage,
  2: required i32 pageSize
}

// 角色
struct ThriftRoleEntity {
  1: required string id,
  2: required string enName,
  3: required string cnName
}

// 用户
struct ThriftUserEntity {
  1: required string id,
  2: required string accountName,
  3: required string password,
  4: required string nickName,
  5: required string idCard,
  6: required i32 deleteFlag
}

// 用户和角色
struct ThriftUserRoleVO {
  1: required string id,
  2: required string accountName,
  3: required string password,
  4: required string nickName,
  5: required string idCard,
  6: required i32 deleteFlag,
  7: optional list<ThriftRoleEntity> roleList
}


// 用户模块提供的服务
service ThriftUserService {
  //根据用户名获取他的账户信息和角色信息
  ThriftUserRoleVO getByAccountName(1: string accountName);
}