DROP TABLE IF EXISTS t_role;
CREATE TABLE t_role(
   id VARCHAR(64) PRIMARY KEY COMMENT '主键',
   cn_name VARCHAR(64) NOT NULL COMMENT '中文名',
   en_name VARCHAR(64) NOT NULL COMMENT '英文名',
   create_date DATETIME NOT NULL COMMENT '创建时间',
   last_modify_date DATETIME NOT NULL COMMENT '最后修改时间',
   last_modify_by VARCHAR(64) NOT NULL COMMENT '最后修改人',
   version INT(11) DEFAULT 0 COMMENT '版本号',
   delete_flag INT(1) NOT NULL COMMENT '删除状态,0-正常,1删除'
);

DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user(
   id VARCHAR(64) PRIMARY KEY COMMENT '主键',
   cn_name VARCHAR(64) NOT NULL COMMENT '中文名',
   en_name VARCHAR(64) NOT NULL COMMENT '英文名',
   create_date DATETIME NOT NULL COMMENT '创建时间',
   last_modify_date DATETIME NOT NULL COMMENT '最后修改时间',
   last_modify_by VARCHAR(64) NOT NULL COMMENT '最后修改人',
   version INT(11) DEFAULT 0 COMMENT '版本号',
   delete_flag INT(1) NOT NULL COMMENT '删除状态,0-正常,1删除'
);