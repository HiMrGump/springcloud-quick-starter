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
  2: required string name,
  3: required string code
}

// 用户
struct ThriftUserEntity {
  1: required string id,
  2: required string accountName,
  3: required string password,
  4: required string userAlias,
  5: required string idCard,
  6: required string userMobile,
  7: required string userEmail,
  8: required string userAvatar,
  9: required i32 enable,
  10: required string createDate,
  11: required i32 deleteFlag
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


struct ThriftResponseResult {
  1: required string code,
  2: required string msg,
  3: required string data
}




// 用户模块提供的服务
service ThriftUserService {
  //根据用户名获取他的账户信息和角色信息
  ThriftResponseResult getByAccountName(1: string accountName);
}