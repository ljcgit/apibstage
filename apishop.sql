/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云服务器
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : 203.195.133.72:3306
 Source Schema         : apishop

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 11/04/2019 21:14:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for api_code
-- ----------------------------
DROP TABLE IF EXISTS `api_code`;
CREATE TABLE `api_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态码',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  `info_id` int(11) NULL DEFAULT NULL COMMENT 'api_infoId',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKln8jibf4q6efr7f5h3mwiw5sf`(`info_id`) USING BTREE,
  CONSTRAINT `FKln8jibf4q6efr7f5h3mwiw5sf` FOREIGN KEY (`info_id`) REFERENCES `api_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for api_info
-- ----------------------------
DROP TABLE IF EXISTS `api_info`;
CREATE TABLE `api_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接口地址',
  `protocol` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'HTTP' COMMENT '请求协议',
  `method` int(2) NOT NULL DEFAULT 0 COMMENT '请求方式   0:GET  1:POST',
  `request_format` int(2) NULL DEFAULT 0 COMMENT '请求格式',
  `response_format` int(2) NULL DEFAULT 0 COMMENT '返回格式',
  `example_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求示例',
  `status` int(2) NOT NULL DEFAULT 0 COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for api_parameter
-- ----------------------------
DROP TABLE IF EXISTS `api_parameter`;
CREATE TABLE `api_parameter`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parameter_name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数名',
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '类型',
  `required` int(2) NOT NULL DEFAULT 0 COMMENT '是否必填  0：否 1：是',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  `kind` int(2) NOT NULL DEFAULT 0 COMMENT '0：请求参数    1：返回参数',
  `info_id` int(11) NULL DEFAULT NULL COMMENT 'api_infoId',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKtndj1rj9cuguxm78upmlr4xgl`(`info_id`) USING BTREE,
  CONSTRAINT `FKtndj1rj9cuguxm78upmlr4xgl` FOREIGN KEY (`info_id`) REFERENCES `api_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(20) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限名称',
  `acl_module_id` int(11) NULL DEFAULT 1 COMMENT '权限所在的权限模块ID',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '请求的URL，可以填正则表达式',
  `type` int(11) NOT NULL DEFAULT 3 COMMENT '类型：1：菜单；2：按钮；3：其他',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态：1：正常；0：冻结',
  `seq` int(11) NOT NULL COMMENT '权限在当前模块下的顺序，由小到大',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_acl_aclmodule`(`acl_module_id`) USING BTREE,
  CONSTRAINT `fk_acl_aclmodule` FOREIGN KEY (`acl_module_id`) REFERENCES `sys_acl_module` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_acl_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_module`;
CREATE TABLE `sys_acl_module`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限模块ID',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限模块名称',
  `parent_id` int(11) NOT NULL DEFAULT 1 COMMENT '上级权限模块ID',
  `level` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限模块层级',
  `seq` int(11) NOT NULL DEFAULT 0 COMMENT '权限模块在当前层级下的顺序，由小到大',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态：1：正常，0：冻结',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_acl_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl_roles`;
CREATE TABLE `sys_acl_roles`  (
  `acl_bean_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL,
  PRIMARY KEY (`acl_bean_id`, `roles_id`) USING BTREE,
  INDEX `FKth054afd4edi2g18n266wrolc`(`roles_id`) USING BTREE,
  CONSTRAINT `FK5rhdd0nyf93mdirhk5sjsr3fr` FOREIGN KEY (`acl_bean_id`) REFERENCES `sys_acl` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKth054afd4edi2g18n266wrolc` FOREIGN KEY (`roles_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL DEFAULT 0 COMMENT '\'权限更新的类型，1：用户；2：权限模块；3：权限；4：角色；5：角色用户关系；6：角色权限关系\'',
  `target_id` int(11) NOT NULL COMMENT '基于type后指定的对象id，比如用户，权限，角色等表的主键',
  `old_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '旧值',
  `new_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '新值',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '当前是否复原过，0：没有；1：复原过',
  `operator` int(11) NOT NULL,
  `operate_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` int(11) NOT NULL DEFAULT 1 COMMENT '角色的类型，1：普通，2：其他',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态，1：可用，0：冻结',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `acl_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_role_roleacl`(`role_id`) USING BTREE,
  INDEX `fk_acl_roleacl`(`acl_id`) USING BTREE,
  CONSTRAINT `fk_acl_roleacl` FOREIGN KEY (`acl_id`) REFERENCES `sys_acl` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_role_roleacl` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_role_roleuser`(`role_id`) USING BTREE,
  INDEX `fk_user_roleuser`(`user_id`) USING BTREE,
  CONSTRAINT `fk_role_roleuser` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_roleuser` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '邮箱地址',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '用户状态：1：正常；0：冻结；2：删除',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
