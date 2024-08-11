/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : jfinal_runapi

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 12/06/2023 09:23:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_source
-- ----------------------------
DROP TABLE IF EXISTS `data_source`;
CREATE TABLE `data_source`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `db_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'dbType',
  `jdbc_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'jdbcUrl',
  `user` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'user',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'password',
  `config_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'configName',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `state` int(2) NULL DEFAULT 1 COMMENT '开机启动（0：否，1：是）',
  `start` int(2) NULL DEFAULT 0 COMMENT '启动（0：否，1：是）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用系统地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '数据源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_source
-- ----------------------------
INSERT INTO `data_source` VALUES ('j9r7bu8uvjv01xvyk8k5lauxn', 'mysql', 'jdbc:mysql://localhost/db_shopping?characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true&ser', 'root', 'root', 'shopping', '小程序商城', 1, 1, '2023-05-18 14:20:36', 'http://localhost');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父类id',
  `object_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联对象id（项目、分享）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '目录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (42, '项目', 1, 0, 'cc1de337cef34b908375d86d9750d70e');
INSERT INTO `menu` VALUES (43, '分享', 1, 0, 'cc1de337cef34b908375d86d9750d70e');
INSERT INTO `menu` VALUES (44, '接口', 1, 0, 'cc1de337cef34b908375d86d9750d70e');

-- ----------------------------
-- Table structure for menu_run_api
-- ----------------------------
DROP TABLE IF EXISTS `menu_run_api`;
CREATE TABLE `menu_run_api`  (
  `menu_id` int(11) NOT NULL,
  `run_api_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`menu_id`, `run_api_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '目录接口' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_run_api
-- ----------------------------
INSERT INTO `menu_run_api` VALUES (42, '43cd794598e94d4ea79fb92eb7e67d67');
INSERT INTO `menu_run_api` VALUES (43, '570016e70af2470cb2dda3ffa0e85993');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `project_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `project_host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目地址',
  `config_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `visit_type` int(2) NULL DEFAULT NULL COMMENT '访问类型（0：公开访问，1：密码访问）',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问密码',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '项目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('320a0af278264d068d00e814328e7e0a', '测试项目', 'http://localhost:8080', NULL, 0, NULL, NULL, 'admin', '2023-06-11 15:09:38');
INSERT INTO `project` VALUES ('cc1de337cef34b908375d86d9750d70e', 'RunApi', 'http://localhost:8080', NULL, 1, '123456', NULL, 'admin', '2023-06-10 20:41:11');

-- ----------------------------
-- Table structure for run_api
-- ----------------------------
DROP TABLE IF EXISTS `run_api`;
CREATE TABLE `run_api`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `action_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口路径',
  `controller` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控制器',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口标题',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口描述',
  `request_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求路径',
  `request_mode` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方式',
  `request_headers` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求Headers参数',
  `request_body_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求的bodyType类型',
  `request_param` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求query参数',
  `request_param_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求query参数说明',
  `request_result` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求结果，保存最新记录',
  `request_body` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求body参数',
  `request_body_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求body参数说明',
  `request_form_data` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求form-data的参数',
  `success_demo` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求成功返回示例',
  `success_demo_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求成功返回示例参数说明',
  `failuer_demo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求失败返回示例',
  `failuer_demo_explain` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求失败返回示例参数说明',
  `request_remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求接口文档备注',
  `interface_status` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口状态',
  `response_headers` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应头',
  `project_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '项目id',
  `sort` int(11) NULL DEFAULT 1 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统接口' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of run_api
-- ----------------------------
INSERT INTO `run_api` VALUES ('089a9482cd5a49b787a0486c53a68239', '/runApi/http/sync', 'com.jfinal.app.runapi.runapi.ctrl.RunApiDemoController', '通过调用/runapi/batchSync同步数据时,将此方法代码复制到你的项目中使用(适合JFinal项目)', '调用http接口同步', '通过调用/runapi/batchSync接口同步数据时,将此方法代码复制到你的项目中使用(适合JFinal项目)，代码位置：com.jfinal.app.runapi.runapi.ctrl.RunApiDemoController', 'http://localhost:8080/runApi/http/sync', 'POST', '[]', 'x-www-form-urlencoded', '', '[]', '', '', '[]', '[]', '', '[]', '', '[]', '', '', '', 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('0b518581bbc549be8467126b53c7e595', '/project', 'com.jfinal.app.runapi.project.ctrl.ProjectController', '项目首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('181aed4bfa4d4b7c8684cf0aa8da950b', '/share/edit', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享编辑页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('1f9d23a0dad64826a52bc2cbc5984bfe', '/shareRunApi/delete', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '分享接口关系删除', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('21711459598f4da7978b15a94eb398d0', '/runapi/update', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口修改', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('249f5c0712d14d35af6aee0ac5277a4d', '/shareRunApi/saveApi', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '保存分享接口关联关系', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('260644f0aae84ea9a585f13dbd595fac', '/', 'com.jfinal.app.runapi.index.IndexController', '前台首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('26c3e62387cb404899316a04d4de8e16', '/captcha', 'com.jfinal.app.runapi.index.IndexController', '验证码', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('27ce5302e0464a67b1e853b4bee3954f', '/menu/getApiList', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录接口文档查询', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('27fdc681935847e397e61ae0ef034ef4', '/dataSource/list', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源查询列表', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('2881f3e15a6049d2868bf4fb1d127980', '/menu', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('29df54fa97634131962c2ae94485c79b', '/runapi/add', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口添加页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('2aa3105ab041422bb6ed16207ca010b1', '/runapi/delete', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口删除', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('2dbd7ad2311a45a381f7622cc6773951', '/share', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('3170eb24bfe74657a5952116d2921df7', '/logout', 'com.jfinal.app.runapi.login.ctrl.LoginController', '退出登录', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('318c5b2071a1464faed8ad050e8e879f', '/project/add', 'com.jfinal.app.runapi.project.ctrl.ProjectController', '项目添加页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('32ddfa30bf7748109e5d232aba814b4c', '/share/delete', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享删除', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('33a46222c9cc42ad8a003cf7903b2d64', '/login', 'com.jfinal.app.runapi.login.ctrl.LoginController', '登录首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('3bddf5367ce347af976d7ed5a4a3ef3e', '/runapi/saveSort', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '排序保存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('4015f96963a447939ee68c7e52861608', '/runapi/saveApi', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口文档保存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('40b5dfac46354756bd0b9cf2e34013c9', '/share/save', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享保存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('41a513ea26eb416e8ad2189bcc0e19d4', '/project/update', 'com.jfinal.app.runapi.project.ctrl.ProjectController', '项目修改', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('43966b600d704116b93d125eca4a781f', '/runapi/save', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口文档保存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('43cd794598e94d4ea79fb92eb7e67d67', '/project/list', 'com.jfinal.app.runapi.project.ctrl.ProjectController', '项目列表', '项目列表', '', 'http://localhost:8080/project/list', 'POST', '[]', 'x-www-form-urlencoded', '', '[]', '{\"code\":0,\"endIndex\":0,\"intIds\":[],\"list\":[{\"create_by\":\"admin\",\"password\":\"123456\",\"create_time\":\"2023-06-10 20:41:11\",\"project_host\":\"http://localhost:8080\",\"visit_type\":1,\"id\":\"cc1de337cef34b908375d86d9750d70e\",\"project_name\":\"RunApi\"}],\"longIds\":[],\"msg\":\"获取成功\",\"pageNumber\":1,\"pageSize\":10,\"startIndex\":0,\"total\":1,\"totalCount\":1,\"totalPage\":1}', '', '[]', '[{\"disable\":true,\"name\":\"pageNumber\",\"type\":\"int\",\"value\":\"1\",\"require\":\"0\",\"remark\":\"分页\"},{\"disable\":true,\"name\":\"pageSize\",\"type\":\"int\",\"value\":\"20\",\"require\":\"0\",\"remark\":\"分页大小\"},{\"disable\":true,\"name\":\"project_name\",\"type\":\"string\",\"value\":\"\",\"require\":\"0\",\"remark\":\"项目名称\"}]', '{\n    \"code\": 0,\n    \"endIndex\": 0,\n    \"intIds\": [],\n    \"list\": [\n        {\n            \"create_by\": \"admin\",\n            \"password\": \"123456\",\n            \"create_time\": \"2023-06-10 20:41:11\",\n            \"project_host\": \"http://localhost:8080\",\n            \"visit_type\": 1,\n            \"id\": \"cc1de337cef34b908375d86d9750d70e\",\n            \"project_name\": \"RunApi\"\n        }\n    ],\n    \"longIds\": [],\n    \"msg\": \"获取成功\",\n    \"pageNumber\": 1,\n    \"pageSize\": 10,\n    \"startIndex\": 0,\n    \"total\": 1,\n    \"totalCount\": 1,\n    \"totalPage\": 1\n}', '[{\"name\":\"code\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"endIndex\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"intIds\",\"type\":\"array\",\"remark\":\"\"},{\"name\":\"list\",\"type\":\"array\",\"remark\":\"项目列表\"},{\"name\":\"list.create_by\",\"type\":\"string\",\"remark\":\"创建人\"},{\"name\":\"list.create_time\",\"type\":\"string\",\"remark\":\"创建时间\"},{\"name\":\"list.project_host\",\"type\":\"string\",\"remark\":\"项目地址，调试接口初始化使用\"},{\"name\":\"list.visit_type\",\"type\":\"string\",\"remark\":\"项目文档访问类型（0：公开访问，1：密码访问）\"},{\"name\":\"list.password\",\"type\":\"string\",\"remark\":\"项目文档访问密码\"},{\"name\":\"list.id\",\"type\":\"string\",\"remark\":\"项目id\"},{\"name\":\"list.project_name\",\"type\":\"string\",\"remark\":\"项目名称\"},{\"name\":\"longIds\",\"type\":\"array\",\"remark\":\"\"},{\"name\":\"msg\",\"type\":\"string\",\"remark\":\"\"},{\"name\":\"pageNumber\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"pageSize\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"startIndex\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"total\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"totalCount\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"totalPage\",\"type\":\"number\",\"remark\":\"\"}]', '', '[]', '', '已完成', 'connection: keep-alive\ncontent-type: application/json;charset=UTF-8\ndate: Sun, 11 Jun 2023 06:44:47 GMT\nserver: JFinal 5.0.4\ntransfer-encoding: chunked\n', 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('4e9588f7a9144c688b274e5f2b35ea96', '/runapi/sendRequest', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口调试代理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('5014084524884688a4c84ba7add7d160', '/menu/edit', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录编辑页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('508990b6b7eb433aa68d5de1b5355b0f', '/dataSource/update', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源修改接口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('5671dcc4574e4d7e83407fec9bc43f9c', '/dataSource/updateFieldValue', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源修改字段接口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('56e0a6be7e104610adef6e07557e65ba', '/menu/updateField', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录修改字段值', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('570016e70af2470cb2dda3ffa0e85993', '/share/list', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享列表', '分享列表', '', 'http://localhost:8080/share/list', 'POST', '[]', 'x-www-form-urlencoded', '', '[]', '{\"code\":0,\"endIndex\":0,\"intIds\":[],\"list\":[{\"password\":\"123456\",\"expire_time\":\"2024-07-31 00:00:00\",\"visit_type\":1,\"id\":\"98f73e420a9a433d9adfe8d361b97f9f\",\"state\":1,\"title\":\"分享文档，便于多个项目的文档共享\"}],\"longIds\":[],\"msg\":\"获取成功\",\"pageNumber\":1,\"pageSize\":10,\"startIndex\":0,\"total\":1,\"totalCount\":1,\"totalPage\":1}', '', '[]', '[]', '{\n    \"code\": 0,\n    \"endIndex\": 0,\n    \"intIds\": [],\n    \"list\": [\n        {\n            \"password\": \"123456\",\n            \"expire_time\": \"2024-07-31 00:00:00\",\n            \"visit_type\": 1,\n            \"id\": \"98f73e420a9a433d9adfe8d361b97f9f\",\n            \"state\": 1,\n            \"title\": \"分享文档，便于多个项目的文档共享\"\n        }\n    ],\n    \"longIds\": [],\n    \"msg\": \"获取成功\",\n    \"pageNumber\": 1,\n    \"pageSize\": 10,\n    \"startIndex\": 0,\n    \"total\": 1,\n    \"totalCount\": 1,\n    \"totalPage\": 1\n}', '[{\"name\":\"code\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"list\",\"type\":\"array\",\"remark\":\"\"},{\"name\":\"list.id\",\"type\":\"string\",\"remark\":\"分享id\"},{\"name\":\"list.title\",\"type\":\"string\",\"remark\":\"分享标题\"},{\"name\":\"list.password\",\"type\":\"string\",\"remark\":\"访问密码\"},{\"name\":\"list.visit_type\",\"type\":\"string\",\"remark\":\"访问类型（0：公开访问，1：密码访问）\"},{\"name\":\"list.state\",\"type\":\"string\",\"remark\":\"有效期类型（0：长期，1：限期，失效时间为expire_time）\"},{\"name\":\"list.expire_time\",\"type\":\"string\",\"remark\":\"有效时间\"}]', '', '[]', '', '', 'connection: keep-alive\ncontent-type: application/json;charset=UTF-8\ndate: Mon, 12 Jun 2023 01:07:52 GMT\nserver: JFinal 5.0.4\ntransfer-encoding: chunked\n', 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('601dfe187f6c4fdb81d24f94069437b8', '/menu/update', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录修改', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('6357a78f339a40b8aa62ccbeaad49c07', '/share/download', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享文档下载', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('6358799259ac415dadbb96c0ee831acc', '/dataSource/save', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源保存接口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('66a51361cfec42b8a590b81916546794', '/runapi/sync2', 'com.test.ctrl.Test.controller', '测试2', '测试2', '测接口', 'http://localhost:8080/runapi/sync2', 'POST', '[]', 'x-www-form-urlencoded', '', '[]', '<h1 style=\"text-align: center\">文档不存在</h1>', '', '[]', '[]', '', '[]', '<h1 style=\"text-align: center\">文档不存在</h1>', '[]', '', '', 'connection: keep-alive\ncontent-type: text/html;charset=UTF-8\ndate: Sun, 11 Jun 2023 07:08:13 GMT\nserver: JFinal 5.0.4\ntransfer-encoding: chunked\n', '320a0af278264d068d00e814328e7e0a', 1);
INSERT INTO `run_api` VALUES ('6d19b7c4a05f45ccb2b0866a51474aaa', '/home', 'com.jfinal.app.runapi.index.IndexController', '后台管理首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('6daccdeaadeb405ab9f2982fe8bd120a', '/runapi/batchSync', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', 'http接收数据接口', 'http接口同步数据', '通过调用此http接口同步项目接口数据', 'http://localhost:8080/runapi/batchSync', 'POST', '[]', 'json', '', '[]', '{\"msg\":\"接口更新成功，共更新接口 : 1\",\"state\":\"ok\"}', '{\n    \"data\": [\n        {\n            \"action_key\": \"/runapi/sync\",\n            \"controller\": \"com.test.ctrl.Test.controller\",\n            \"remark\": \"测试1\",\n            \"project_id\": \"320a0af278264d068d00e814328e7e0a\"\n        },  {\n            \"action_key\": \"/runapi/sync2\",\n            \"controller\": \"com.test.ctrl.Test.controller\",\n            \"remark\": \"测试2\",\n            \"project_id\": \"320a0af278264d068d00e814328e7e0a\"\n        }\n    ]\n}', '[{\"name\":\"data\",\"type\":\"array\",\"require\":\"1\",\"remark\":\"接口数据\"},{\"name\":\"data.action_key\",\"type\":\"string\",\"require\":\"1\",\"remark\":\"接口路径\"},{\"name\":\"data.controller\",\"type\":\"string\",\"require\":\"1\",\"remark\":\"接口控制器名称\"},{\"name\":\"data.remark\",\"type\":\"string\",\"require\":\"1\",\"remark\":\"接口备注信息\"},{\"name\":\"data.project_id\",\"type\":\"string\",\"require\":\"1\",\"remark\":\"项目id（需要提前创建项目）\"}]', '[]', '{\n    \"msg\": \"接口更新成功，共更新接口 : 1\",\n    \"state\": \"ok\"\n}', '[{\"name\":\"msg\",\"type\":\"string\",\"remark\":\"\"},{\"name\":\"state\",\"type\":\"string\",\"remark\":\"\"}]', '', '[]', '在项目中使用代码，参考RunApiDemoController的sync()方法，jfinal项目复制使用即可。', '', 'connection: keep-alive\ncontent-type: application/json;charset=UTF-8\ndate: Sun, 11 Jun 2023 06:59:05 GMT\nserver: JFinal 5.0.4\ntransfer-encoding: chunked\n', 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('6dbcf378e9b1498a841a4e847428d33a', '/runapi/download', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口文档下载', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('73552d6f08a14ac6b82b7f08b7afd67c', '/shareRunApi/edit', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '分享接口关系编辑页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('768cc12d9bd34ea392927044fd14f39b', '/shareRunApi/deleteApi', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '删除分享接口关联关系', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('76f240f1407744e3a389a59e7f07bf70', '/runapi', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '项目文档入口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('895acd55a40e426c8713905cfe6c316e', '/runapi/main', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口管理页面', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('8987a51df26947e0997563ff38cd2da7', '/runapi/sync', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口同步更新', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('89b4e31ae61e4591ad70dcf860a37340', '/login/doLogin', 'com.jfinal.app.runapi.login.ctrl.LoginController', '登录接口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('90e56c00d85941a282048e36b5372c66', '/shareRunApi/add', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '分享接口关系添加页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('95aca0ded1e4483a91615edb3e8895db', '/menu/delete', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录删除', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('9c6c73f5c62549c58e4a44052369b648', '/menu/add', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录添加页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('9fb0a7015f4f4798973e8187eb39d70b', '/project/edit', 'com.jfinal.app.runapi.project.ctrl.ProjectController', '项目编辑页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('a1a4e766543846e8ad9158230e2d9208', '/shareRunApi/list', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '分享接口关系列表', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('a41afcf9b6d2490992ed031b6ae4d450', '/project/delete', 'com.jfinal.app.runapi.project.ctrl.ProjectController', '项目删除', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('a59c8f26e67f4c3f947ab89b0356a7e0', '/share/add', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享添加页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('a750a1269e7b49ebbc7a12f4cfa43a6b', '/share/update', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享修改', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('a851ced87a0743188fa88e9a20689d1b', '/dataSource/add', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源添加页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('afd690be597b414ea75996174b1ae5db', '/dataSource/debug', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源连接测试接口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('b40a09e333874757bbc0600cd90cc458', '/menu/getSelectList', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录下拉列表', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('b8155fb3c29f4ccc8d692b21a7b6b824', '/menu/deleteMenuApi', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '删除目录Api关联关系', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('b84254fdbd4a4687aeb0293c653dba75', '/menu/getTreeList', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录树列表', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('bc60b805d823437d80893f3d8a71a429', '/shareRunApi', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '分享接口关系首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('be2a030eb4a74db793f75832915ba997', '/dataSource/start', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源启动接口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('be54c903d058413c8ee80f97fd589619', '/menu/save', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录保存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('c012b071e0cf47bd8e78e14f35eb9488', '/dataSource', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('c419f4b0ad144ec8b3a8a48c34a78211', '/menu/list', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录分页列表', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('c5357fe215d845c78d82a635a88cd886', '/runapi/list', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口列表', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('c8f24dffd0464a1584ed6d683acfcc48', '/runapi/debug', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口调试页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('de03729203334df094521c3c8e7bc500', '/shareRunApi/update', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '分享接口关系修改', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('de473542497f4e5ba5cf1847ce58d1ae', '/doc', 'com.jfinal.app.runapi.share.ctrl.ShareController', '分享文档入口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('df5199be65934032913673597feb72f7', '/runapi/edit', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口编辑页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('e11dbc0304ca4fe58ebed9036e0488b2', '/dataSource/delete', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源删除接口', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('e1c57422ac104381b1e210bbfdb76ff7', '/runapi/sort', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '排序', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('e8db2f60488e4c3ca660e6b707ef780c', '/menu/saveMenuApi', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '保存目录Api', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('ee1b5ffd43da4efeb353f9ff05eb31bb', '/dataSource/edit', 'com.jfinal.app.runapi.datasource.ctrl.DataSourceController', '数据源编辑页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('f2c16b49bd43480b8fe8d082222bccdc', '/project/save', 'com.jfinal.app.runapi.project.ctrl.ProjectController', '项目保存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('f447d0e52e02462fb262f9c6b95adbae', '/runapi/addDoc', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口文档添加首页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('f45b359602a44b2eb673c5fbee58c7c3', '/shareRunApi/save', 'com.jfinal.app.runapi.sharerunapi.ctrl.ShareRunApiController', '分享接口关系保存', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('f4cc415234aa4dd8887b6c3324565ab8', '/runapi/doc', 'com.jfinal.app.runapi.runapi.ctrl.RunApiController', '接口文档管理页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);
INSERT INTO `run_api` VALUES ('fd19c8efd5264ab2baa515ff448b46ba', '/menu/addApi', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录Api添加页', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'cc1de337cef34b908375d86d9750d70e', 1);

-- ----------------------------
-- Table structure for share
-- ----------------------------
DROP TABLE IF EXISTS `share`;
CREATE TABLE `share`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `visit_type` int(2) NULL DEFAULT 0 COMMENT '访问类型（0：免密访问，1：密码访问）',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问密码',
  `config_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据源配置项',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '到期时间',
  `state` int(2) NULL DEFAULT NULL COMMENT '有效期限（0：长期，1：限期）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分享记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of share
-- ----------------------------
INSERT INTO `share` VALUES ('98f73e420a9a433d9adfe8d361b97f9f', '分享文档，便于多个项目的文档共享', 1, '123456', NULL, '2024-07-31 00:00:00', 1);

-- ----------------------------
-- Table structure for share_run_api
-- ----------------------------
DROP TABLE IF EXISTS `share_run_api`;
CREATE TABLE `share_run_api`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `run_api_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口id',
  `share_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分享文档关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of share_run_api
-- ----------------------------
INSERT INTO `share_run_api` VALUES (6, '570016e70af2470cb2dda3ffa0e85993', '98f73e420a9a433d9adfe8d361b97f9f');
INSERT INTO `share_run_api` VALUES (8, '6daccdeaadeb405ab9f2982fe8bd120a', '98f73e420a9a433d9adfe8d361b97f9f');

SET FOREIGN_KEY_CHECKS = 1;
