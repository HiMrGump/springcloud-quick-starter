DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role(
   id VARCHAR(64) PRIMARY KEY COMMENT '主键',
   name VARCHAR(64) NOT NULL COMMENT '角色名',
   code VARCHAR(64) NOT NULL COMMENT '角色代码',
   create_date DATETIME NOT NULL COMMENT '创建时间',
   last_modify_date DATETIME COMMENT '最后修改时间',
   last_modify_by VARCHAR(64) COMMENT '最后修改人',
   version INT(11) DEFAULT 0 COMMENT '版本号',
   delete_flag INT(1) NOT NULL COMMENT '删除状态,0-正常,1删除'
) ENGINE=INNODB CHARACTER SET utf8 COMMENT='角色表';

DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user(
   id VARCHAR(64) PRIMARY KEY COMMENT '主键',
   account_name VARCHAR(64) NOT NULL COMMENT '账户名',
   password VARCHAR(64) NOT NULL COMMENT '密码',
   user_alias VARCHAR(32) NOT NULL COMMENT '别名',
   user_avatar VARCHAR(64) NOT NULL COMMENT '头像信息',
   id_card VARCHAR(32) NOT NULL COMMENT '身份证号',
   user_mobile VARCHAR(15) NOT NULL COMMENT '手机号',
   user_email VARCHAR(64) NOT NULL COMMENT '邮箱',
   enable INT(1) NOT NULL COMMENT '是否允许登录,1-允许登陆 0-不允许登陆',
   create_date DATETIME NOT NULL COMMENT '创建时间',
   last_modify_date DATETIME COMMENT '最后修改时间',
   last_modify_by VARCHAR(64) COMMENT '最后修改人',
   version INT(11) DEFAULT 0 COMMENT '版本号',
   delete_flag INT(1) NOT NULL COMMENT '删除状态,0-正常,1删除'
)ENGINE=INNODB CHARACTER SET utf8 COMMENT='用户表';

DROP TABLE IF EXISTS t_user_role;
CREATE TABLE t_user_role(
   user_id VARCHAR(64) NOT NULL COMMENT '用户ID',
   role_id VARCHAR(64) NOT NULL COMMENT '角色ID',
   PRIMARY KEY (user_id,role_id)
)ENGINE=INNODB CHARACTER SET utf8 COMMENT='用户角色中间表';

DROP TABLE IF EXISTS t_function;
CREATE TABLE t_function(
   id VARCHAR(64) PRIMARY KEY COMMENT '主键',
   parent_id VARCHAR(64) DEFAULT '-1' COMMENT '父ID',
   name VARCHAR(64) NOT NULL COMMENT '功能名',
   code VARCHAR(64) NOT NULL COMMENT '权限代码',
   create_date DATETIME NOT NULL COMMENT '创建时间',
   last_modify_date DATETIME COMMENT '最后修改时间',
   last_modify_by VARCHAR(64) COMMENT '最后修改人',
   version INT(11) DEFAULT 0 COMMENT '版本号',
   delete_flag INT(1) NOT NULL COMMENT '删除状态,0-正常,1删除'
) ENGINE=INNODB CHARACTER SET utf8 COMMENT='功能表';

DROP TABLE IF EXISTS t_role_function;
CREATE TABLE t_role_function(
   role_id VARCHAR(64) NOT NULL COMMENT '角色ID',
   function_id VARCHAR(64) NOT NULL COMMENT '功能ID',
   PRIMARY KEY (role_id,function_id)
)ENGINE=INNODB CHARACTER SET utf8 COMMENT='角色功能中间表';