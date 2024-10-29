/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019 (8.0.19)
 Source Host           : localhost:3306
 Source Schema         : apidev

 Target Server Type    : MySQL
 Target Server Version : 80019 (8.0.19)
 File Encoding         : 65001

 Date: 29/10/2024 23:13:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apidev_api
-- ----------------------------
DROP TABLE IF EXISTS `apidev_api`;
CREATE TABLE `apidev_api`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `action_key` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口路径',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口描述',
  `controller` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控制器',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口标题',
  `request_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `request_mode` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_headers` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求Headers参数',
  `request_body_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求的bodyType类型',
  `request_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求query参数',
  `request_param_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求query参数说明',
  `request_path_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求Path参数说明',
  `request_result` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求结果，保存最新记录',
  `request_body` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求body参数',
  `request_body_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求body参数说明',
  `request_form_data` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求form-data的参数',
  `success_demo` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求成功返回示例',
  `success_demo_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求成功返回示例参数说明',
  `failuer_demo` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求失败返回示例',
  `failuer_demo_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求失败返回示例参数说明',
  `request_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求接口文档备注',
  `interface_status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口状态',
  `response_headers` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应头',
  `response_definition` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应定义',
  `response_demo` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应用例',
  `sort` int NULL DEFAULT 1 COMMENT '排序',
  `create_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最新修改时间',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'menu:目录，api:接口，demo：用例，link：快捷请求',
  `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '-1' COMMENT '父级id（1:是接口根目录，2：是快捷请求根目录）',
  `del` int NULL DEFAULT 0 COMMENT '0：恢复正常状态，1：标记已删除，显示在回收站，2：标记下级数据被删除，不显示回收站',
  `visible` int NULL DEFAULT 1 COMMENT '接口是否可见(0:不可见，1：可见)',
  `share_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享id',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问密码',
  `expiret_time` date NULL DEFAULT NULL COMMENT '访问过期时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'API' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
