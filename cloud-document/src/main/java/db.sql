/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : localhost:3306
 Source Schema         : cloud-module

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 23/08/2019 17:03:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

#
# In your Quartz properties file, you'll need to set
# org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#
#
# By: Ron Cordell - roncordell
#  I didn't see this anywhere, so I thought I'd post it here. This is the script from Quartz to create the tables in a MySQL database, modified to use INNODB instead of MYISAM.

DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

CREATE TABLE QRTZ_JOB_DETAILS(
    SCHED_NAME VARCHAR(120) NOT NULL,
    JOB_NAME VARCHAR(190) NOT NULL,
    JOB_GROUP VARCHAR(190) NOT NULL,
    DESCRIPTION VARCHAR(250) NULL,
    JOB_CLASS_NAME VARCHAR(250) NOT NULL,
    IS_DURABLE VARCHAR(1) NOT NULL,
    IS_NONCONCURRENT VARCHAR(1) NOT NULL,
    IS_UPDATE_DATA VARCHAR(1) NOT NULL,
    REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
    JOB_DATA BLOB NULL,
    PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
) ENGINE=InnoDB;

CREATE TABLE QRTZ_TRIGGERS (
     SCHED_NAME VARCHAR(120) NOT NULL,
     TRIGGER_NAME VARCHAR(190) NOT NULL,
     TRIGGER_GROUP VARCHAR(190) NOT NULL,
     JOB_NAME VARCHAR(190) NOT NULL,
     JOB_GROUP VARCHAR(190) NOT NULL,
     DESCRIPTION VARCHAR(250) NULL,
     NEXT_FIRE_TIME BIGINT(13) NULL,
     PREV_FIRE_TIME BIGINT(13) NULL,
     PRIORITY INTEGER NULL,
     TRIGGER_STATE VARCHAR(16) NOT NULL,
     TRIGGER_TYPE VARCHAR(8) NOT NULL,
     START_TIME BIGINT(13) NOT NULL,
     END_TIME BIGINT(13) NULL,
     CALENDAR_NAME VARCHAR(190) NULL,
     MISFIRE_INSTR SMALLINT(2) NULL,
     JOB_DATA BLOB NULL,
     PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
     FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
     REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
     SCHED_NAME VARCHAR(120) NOT NULL,
     TRIGGER_NAME VARCHAR(190) NOT NULL,
     TRIGGER_GROUP VARCHAR(190) NOT NULL,
     REPEAT_COUNT BIGINT(7) NOT NULL,
     REPEAT_INTERVAL BIGINT(12) NOT NULL,
     TIMES_TRIGGERED BIGINT(10) NOT NULL,
     PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
     FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
     REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_CRON_TRIGGERS (
     SCHED_NAME VARCHAR(120) NOT NULL,
     TRIGGER_NAME VARCHAR(190) NOT NULL,
     TRIGGER_GROUP VARCHAR(190) NOT NULL,
     CRON_EXPRESSION VARCHAR(120) NOT NULL,
     TIME_ZONE_ID VARCHAR(80),
     PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
     FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
     REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_SIMPROP_TRIGGERS(
     SCHED_NAME VARCHAR(120) NOT NULL,
     TRIGGER_NAME VARCHAR(190) NOT NULL,
     TRIGGER_GROUP VARCHAR(190) NOT NULL,
     STR_PROP_1 VARCHAR(512) NULL,
     STR_PROP_2 VARCHAR(512) NULL,
     STR_PROP_3 VARCHAR(512) NULL,
     INT_PROP_1 INT NULL,
     INT_PROP_2 INT NULL,
     LONG_PROP_1 BIGINT NULL,
     LONG_PROP_2 BIGINT NULL,
     DEC_PROP_1 NUMERIC(13,4) NULL,
     DEC_PROP_2 NUMERIC(13,4) NULL,
     BOOL_PROP_1 VARCHAR(1) NULL,
     BOOL_PROP_2 VARCHAR(1) NULL,
     PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
     FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
     REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_BLOB_TRIGGERS (
     SCHED_NAME VARCHAR(120) NOT NULL,
     TRIGGER_NAME VARCHAR(190) NOT NULL,
     TRIGGER_GROUP VARCHAR(190) NOT NULL,
     BLOB_DATA BLOB NULL,
     PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
     INDEX (SCHED_NAME,TRIGGER_NAME, TRIGGER_GROUP),
     FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
     REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_CALENDARS (
     SCHED_NAME VARCHAR(120) NOT NULL,
     CALENDAR_NAME VARCHAR(190) NOT NULL,
     CALENDAR BLOB NOT NULL,
     PRIMARY KEY (SCHED_NAME,CALENDAR_NAME)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
     SCHED_NAME VARCHAR(120) NOT NULL,
     TRIGGER_GROUP VARCHAR(190) NOT NULL,
     PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_FIRED_TRIGGERS (
     SCHED_NAME VARCHAR(120) NOT NULL,
     ENTRY_ID VARCHAR(95) NOT NULL,
     TRIGGER_NAME VARCHAR(190) NOT NULL,
     TRIGGER_GROUP VARCHAR(190) NOT NULL,
     INSTANCE_NAME VARCHAR(190) NOT NULL,
     FIRED_TIME BIGINT(13) NOT NULL,
     SCHED_TIME BIGINT(13) NOT NULL,
     PRIORITY INTEGER NOT NULL,
     STATE VARCHAR(16) NOT NULL,
     JOB_NAME VARCHAR(190) NULL,
     JOB_GROUP VARCHAR(190) NULL,
     IS_NONCONCURRENT VARCHAR(1) NULL,
     REQUESTS_RECOVERY VARCHAR(1) NULL,
     PRIMARY KEY (SCHED_NAME,ENTRY_ID)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_SCHEDULER_STATE (
     SCHED_NAME VARCHAR(120) NOT NULL,
     INSTANCE_NAME VARCHAR(190) NOT NULL,
     LAST_CHECKIN_TIME BIGINT(13) NOT NULL,
     CHECKIN_INTERVAL BIGINT(13) NOT NULL,
     PRIMARY KEY (SCHED_NAME,INSTANCE_NAME)
)ENGINE=InnoDB;

CREATE TABLE QRTZ_LOCKS (
     SCHED_NAME VARCHAR(120) NOT NULL,
     LOCK_NAME VARCHAR(40) NOT NULL,
     PRIMARY KEY (SCHED_NAME,LOCK_NAME)
)ENGINE=InnoDB;

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);



-- ----------------------------
-- Table structure for clientdetails
-- ----------------------------
DROP TABLE IF EXISTS `clientdetails`;
CREATE TABLE `clientdetails`  (
  `appId` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resourceIds` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `appSecret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `grantTypes` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `redirectUrl` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additionalInformation` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoApproveScopes` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`appId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`  (
  `userId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clientId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expiresAt` datetime(0) NULL DEFAULT NULL,
  `lastModifiedAt` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('client_1', NULL, '$2a$10$I4rJX8W9U69sHPVHNAXPe.WfLTSwENY74wBL5wuywA7.T4hBlZsHa', 'read,write', 'client_credentials,refresh_token', NULL, 'SIMPLE_USER', 3600, 21600, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('client_2', NULL, '$2a$10$I4rJX8W9U69sHPVHNAXPe.WfLTSwENY74wBL5wuywA7.T4hBlZsHa', 'all', 'password,sms_code,refresh_token', NULL, 'SIMPLE_USER,SIMPLE_ADMIN', 3600, 21600, NULL, NULL);

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_function
-- ----------------------------
DROP TABLE IF EXISTS `t_function`;
CREATE TABLE `t_function`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '-1' COMMENT '父ID',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功能名',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限代码',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `last_modify_date` datetime(0) NOT NULL COMMENT '最后修改时间',
  `last_modify_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最后修改人',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本号',
  `delete_flag` int(1) NOT NULL COMMENT '删除状态,0-正常,1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '功能表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色代码',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `last_modify_date` datetime(0) NOT NULL COMMENT '最后修改时间',
  `last_modify_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最后修改人',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本号',
  `delete_flag` int(1) NOT NULL COMMENT '删除状态,0-正常,1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '超级管理员', 'SUPER_ADMIN', '2019-06-14 11:48:30', '2019-06-14 11:48:32', '1', 0, 0);
INSERT INTO `t_role` VALUES ('2', '普通用户', 'SIMPLE_USER', '2019-06-14 11:45:18', '2019-06-14 11:45:22', '1', 0, 0);
INSERT INTO `t_role` VALUES ('3', '管理员', 'SIMPLE_ADMIN', '2019-06-14 11:47:36', '2019-06-14 11:47:40', '1', 0, 0);

-- ----------------------------
-- Table structure for t_role_function
-- ----------------------------
DROP TABLE IF EXISTS `t_role_function`;
CREATE TABLE `t_role_function`  (
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色ID',
  `function_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功能ID',
  PRIMARY KEY (`role_id`, `function_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色功能中间表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `account_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `user_alias` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '别名',
  `user_avatar` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '头像信息',
  `id_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证号',
  `user_mobile` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `user_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `enable` int(1) NOT NULL COMMENT '是否允许登录,1-允许登陆 0-不允许登陆',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `last_modify_date` datetime(0) NOT NULL COMMENT '最后修改时间',
  `last_modify_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最后修改人',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本号',
  `delete_flag` int(1) NOT NULL COMMENT '删除状态,0-正常,1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '$2a$10$mq.LJO08i3Y3klHpg/R6xu6.pTqgOPqEHL7I.43a0D8QPRqhBtLeS', '苏东坡', 'head.jpg', '362232546213215', '13611111111', '13611111111@boss.com', 0, '2019-06-14 11:44:35', '2019-06-14 11:44:37', '', 0, 0);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色中间表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '2');
INSERT INTO `t_user_role` VALUES ('1', '3');


-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`  (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名称',
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典类型',
  `code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典码',
  `value` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `sort_index` int(4) NULL DEFAULT 0 COMMENT '排序',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `last_modify_date` datetime(0) NOT NULL COMMENT '最后修改时间',
  `last_modify_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最后修改人',
  `delete_flag` int(1) NOT NULL COMMENT '删除状态,0-正常,1删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '数据字典表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
