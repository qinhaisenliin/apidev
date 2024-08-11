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

 Date: 10/08/2024 16:31:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for apidev_api
-- ----------------------------
DROP TABLE IF EXISTS `apidev_api`;
CREATE TABLE `apidev_api`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `action_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口路径',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口描述',
  `controller` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '控制器',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口标题',
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
  `response_definition` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应定义',
  `response_demo` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '响应用例',
  `sort` int NULL DEFAULT 1 COMMENT '排序',
  `create_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '最新修改时间',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'menu:目录，api:接口，demo：用例，link：快捷请求',
  `parent_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '-1' COMMENT '父级id（-1:是接口根目录，-2：是快捷请求根目录）',
  `del` int NULL DEFAULT 0 COMMENT '0：恢复正常状态，1：标记已删除，显示在回收站，2：标记下级数据删除，不显示回收站',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'API' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apidev_api
-- ----------------------------
INSERT INTO `apidev_api` VALUES ('035c9b988f9d4498bdb70b0fae489ff4', NULL, NULL, NULL, NULL, '1231', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'menu', '9966e9e3dcb6476c8b9f8c4ba961bb4e', 0);
INSERT INTO `apidev_api` VALUES ('0816cf99f349467595fb718ab3cf4771', NULL, NULL, NULL, NULL, '功能管理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 20:50:04', NULL, NULL, 'menu', '51ee91a79f654c55b2540a683ad3a668', 0);
INSERT INTO `apidev_api` VALUES ('0d0a5dd943bb44fb98df5e60a0a9aa2a', NULL, NULL, NULL, NULL, '1231', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:49:13', NULL, NULL, 'menu', 'ed8b09418dbd4587bc35af7176f3fa1c', 0);
INSERT INTO `apidev_api` VALUES ('0e96d3172a7a468f9a2684c439f91127', '/apidev/api/1231', NULL, NULL, NULL, NULL, NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:21:00', NULL, '2024-08-09 17:38:35', 'api', '509f3e1c6d414bc3ad6f2830768789cb', 2);
INSERT INTO `apidev_api` VALUES ('123123', '/apidev/api/1231', NULL, NULL, NULL, NULL, NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-07 11:20:04', 'api', 'd9b0117b4ae14915af7277607b115b86', 0);
INSERT INTO `apidev_api` VALUES ('12312353', NULL, NULL, NULL, NULL, '用户管理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('123ew123', NULL, NULL, NULL, NULL, 'test', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'menu', '-2', 0);
INSERT INTO `apidev_api` VALUES ('242da7f0636d43c3bb32e210531a04db', '/apidev/api/run', NULL, 'cn.apidev.api.ctrl.ApiController', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-09 14:19:58', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('25662572150841c0b1cfec817079aa81', '/apidev/api/addMenu', NULL, 'cn.apidev.api.ctrl.ApiController', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-09 17:02:49', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('31b1124f542946889f425c06fe53bab0', '/apidev/api/1231', NULL, NULL, NULL, NULL, NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 20:50:04', NULL, '2024-08-07 11:20:04', 'api', 'b7d8a59ef23b4ed586b1fdc627b67817', 0);
INSERT INTO `apidev_api` VALUES ('3364c118e4a14efa9f20bebd19c75009', NULL, NULL, NULL, NULL, '1212', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:21:00', NULL, NULL, 'menu', '71973339536d4decb462f61a4ee59c08', 0);
INSERT INTO `apidev_api` VALUES ('3494ec8696764dccb0d1ce84ae09dc83', '/apidev/api', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api', '/apidev/api', NULL, 'POST', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-07 16:03:02', 'api', '656fbb9f07834622aabdb9eeeccdd212', 2);
INSERT INTO `apidev_api` VALUES ('351fa0233bb945bda3f229aca56c9f5a', NULL, NULL, NULL, NULL, '1231', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:21:00', NULL, '2024-08-10 13:31:42', 'menu', '720c049b6fec416387f3c54ef7e71a9a', 0);
INSERT INTO `apidev_api` VALUES ('39ca797c166b4c8e923e0f81f6e4a03d', '/apidev/api/debug', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/debug', '/apidev/api/debug（未调试） Copy', NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 17:55:44', NULL, '2024-08-07 17:55:41', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('39dad1b00e6845779a466ca9324cce29', NULL, NULL, NULL, NULL, '147', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-06 21:10:31', NULL, NULL, 'menu', 'undefined', 0);
INSERT INTO `apidev_api` VALUES ('47ae4be67b834dd19e4d92e05a240464', '/apidev/api/getById', NULL, 'cn.apidev.api.ctrl.ApiController', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('49f1fe41be82476ab231c8aa9a06745d', NULL, NULL, NULL, NULL, '4534', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:18:45', NULL, NULL, 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('509f3e1c6d414bc3ad6f2830768789cb', NULL, NULL, NULL, NULL, 'ces14', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:21:00', NULL, '2024-08-09 17:38:35', 'menu', '59b4fac7bc67414099cdb84b3eec5905', 2);
INSERT INTO `apidev_api` VALUES ('51ee91a79f654c55b2540a683ad3a668', NULL, NULL, NULL, NULL, '系统管理 Copy Copy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 20:50:04', NULL, '2024-08-07 10:47:05', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('5570e19daee34349b83cc576797caede', '/apidev/api/getById', NULL, 'cn.apidev.api.ctrl.ApiController', NULL, 'null Copy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 17:58:36', NULL, '2024-08-09 14:19:58', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('595804082559458eb0f326d26afa28ba', NULL, NULL, NULL, NULL, '特斯太1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:03:55', NULL, '2024-08-10 14:29:33', 'menu', 'fab5cc538a2945b987f869fe4e0cf434', 2);
INSERT INTO `apidev_api` VALUES ('59b4fac7bc67414099cdb84b3eec5905', NULL, NULL, NULL, NULL, '功能管理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:21:00', NULL, '2024-08-09 17:38:35', 'menu', '720c049b6fec416387f3c54ef7e71a9a', 2);
INSERT INTO `apidev_api` VALUES ('6554', '24334', NULL, NULL, NULL, '234234', NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'link', '123ew123', 0);
INSERT INTO `apidev_api` VALUES ('656fbb9f07834622aabdb9eeeccdd212', NULL, NULL, '', NULL, '接口管理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-09 11:50:37', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('656fbb9f07834622aabdb9eeeccdd252', '/apidev/api/list', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/list', '/apidev/api/list', NULL, 'DEL', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('66a38f270b354a34ae19d8f6a199dde8', '/apidev/api/info', NULL, 'cn.apidev.api.ctrl.ApiController', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('66a70ea11032417c8ea422de1b3ba67a', '/apidev/api/sync', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/sync', '同步接口2 Copy', NULL, 'PUT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 17:59:09', NULL, '2024-08-07 00:14:31', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('6f33cd31072e4b7c8f7cf595244376fa', NULL, NULL, NULL, NULL, '1231', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 20:50:04', NULL, NULL, 'menu', '51ee91a79f654c55b2540a683ad3a668', 0);
INSERT INTO `apidev_api` VALUES ('70428cac705e4cc8a99bdd4f0e3709bd', NULL, NULL, NULL, NULL, '功能管理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, 'menu', '9966e9e3dcb6476c8b9f8c4ba961bb4e', 0);
INSERT INTO `apidev_api` VALUES ('71973339536d4decb462f61a4ee59c08', NULL, NULL, NULL, NULL, '12312312', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:11:58', NULL, NULL, 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('720c049b6fec416387f3c54ef7e71a9a', NULL, NULL, NULL, NULL, '系统管理 Copy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:21:00', NULL, '2024-08-10 13:30:25', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('784eda53fd5342929c190fc8f2a7fb9c', NULL, NULL, NULL, NULL, 'ces14', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:49:13', NULL, '2024-08-07 11:20:39', 'menu', 'cd7d820535884bb99a2c6688c043b0f5', 0);
INSERT INTO `apidev_api` VALUES ('79af089168c04a299c214d86bf700e09', NULL, NULL, NULL, NULL, 'werer12', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-06 21:25:38', NULL, '2024-08-07 00:17:19', 'menu', '-2', 0);
INSERT INTO `apidev_api` VALUES ('7c3949d3b5524fa2a1f4bcf0dbd3deb7', NULL, NULL, NULL, NULL, '额外企鹅去', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:10:57', NULL, NULL, 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('823b5495c0554c10929c4644e9e838fa', '/menu/getTreeList', '', 'com.jfinal.app.runapi.menu.ctrl.MenuController', '目录树列表', '目录树列表', 'http://localhost:8080/menu/getTreeList', 'POST', '[]', 'x-www-form-urlencoded', '', '[]', '{\"msg\":\"成功\",\"code\":0,\"data\":[],\"count\":0,\"state\":\"ok\"}', '', '[]', '[]', '{\n    \"msg\": \"成功\",\n    \"code\": 0,\n    \"data\": [],\n    \"count\": 0,\n    \"state\": \"ok\"\n}', '[{\"name\":\"msg\",\"type\":\"string\",\"remark\":\"\"},{\"name\":\"code\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"data\",\"type\":\"array\",\"remark\":\"\"},{\"name\":\"count\",\"type\":\"number\",\"remark\":\"\"},{\"name\":\"state\",\"type\":\"string\",\"remark\":\"\"}]', '', '[]', '', '', 'connection: keep-alive\ncontent-type: application/json;charset=UTF-8\ndate: Mon, 13 May 2024 10:46:53 GMT\nserver: JFinal 5.0.4\ntransfer-encoding: chunked\n', NULL, NULL, 31, '1', '2024-04-19 12:04:46', '1', '2024-05-13 18:47:02', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('8297fe52277a423297d18340d1fa1951', NULL, NULL, NULL, NULL, '987', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-06 21:12:15', NULL, '2024-08-10 14:29:51', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('855c9f5bec434bf68e39fb8d3690ca8c', '/apidev/api/debug', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/debug', '/apidev/api/debug（未调试） Copy Copy', NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:36:11', NULL, '2024-08-07 17:55:41', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('85ecf6ccb8fd4080996f6947be7c3d60', '/apidev/api/add', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/add', NULL, NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-10 13:28:11', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('9966e9e3dcb6476c8b9f8c4ba961bb4e', NULL, NULL, NULL, NULL, '系统管理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-07 10:47:05', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('9b195ad1cc844f76987977a8254792ae', NULL, NULL, NULL, NULL, '12', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:19:46', NULL, NULL, 'menu', '49f1fe41be82476ab231c8aa9a06745d', 0);
INSERT INTO `apidev_api` VALUES ('9fdc556184e74f72bedab0bedde16da4', '/apidev/api/add', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/add', '/apidev/api/add（未调试） Copy', NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:13:50', NULL, NULL, 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('a591fc48e7dc4d7eac1f25e1fca9eb15', NULL, NULL, NULL, NULL, 'ces14', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:16:38', NULL, '2024-08-07 18:20:57', 'menu', 'b0825be909a14ec197bb446bb77c29c4', 2);
INSERT INTO `apidev_api` VALUES ('aa27a3c6261d450a91fa80edbf44bd9d', '/apidev/api/1231', NULL, NULL, NULL, NULL, NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:49:13', NULL, '2024-08-07 11:20:04', 'api', '784eda53fd5342929c190fc8f2a7fb9c', 0);
INSERT INTO `apidev_api` VALUES ('aed5373384c04541bcab79e2aca3f17d', '/apidev/api/save', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/save', '保存接口', NULL, 'POST', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-09 10:53:39', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('b0102bf055264875ae3cd65950f2310d', '/apidev/api/doc', NULL, 'cn.apidev.api.ctrl.ApiController', NULL, '/apidev/api/doc（未调试）', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-07 17:57:34', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('b0825be909a14ec197bb446bb77c29c4', NULL, NULL, NULL, NULL, '功能管理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:16:38', NULL, '2024-08-07 18:20:57', 'menu', 'c3729b4006104381b6a32f3539449e94', 2);
INSERT INTO `apidev_api` VALUES ('b25787642ecb42079f82691e6a07332f', NULL, NULL, NULL, NULL, '12312', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-06 21:07:55', NULL, '2024-08-10 14:20:51', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('b26d65a39304490eb21fd8d155a9b5d8', NULL, NULL, NULL, NULL, '1231', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:16:38', NULL, '2024-08-07 18:20:57', 'menu', 'c3729b4006104381b6a32f3539449e94', 2);
INSERT INTO `apidev_api` VALUES ('b26f621c03c14122bf00d4de56a8dc05', NULL, NULL, NULL, NULL, 'asdasdas', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-10 14:33:27', 'menu', '-2', 0);
INSERT INTO `apidev_api` VALUES ('b2d8bb8a486b4e60a6c56d45b6d5895f', NULL, NULL, NULL, NULL, 'ewf123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-06 21:25:28', NULL, '2024-08-10 14:54:30', 'menu', '-2', 1);
INSERT INTO `apidev_api` VALUES ('b7d8a59ef23b4ed586b1fdc627b67817', NULL, NULL, NULL, NULL, 'ces14', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 20:50:04', NULL, '2024-08-07 11:20:39', 'menu', '0816cf99f349467595fb718ab3cf4771', 0);
INSERT INTO `apidev_api` VALUES ('bbeeaba4c2044babaff2fa173f217e66', NULL, NULL, NULL, NULL, '12312', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-06 21:15:36', NULL, NULL, 'menu', '123ew123', 0);
INSERT INTO `apidev_api` VALUES ('c17988778e294bf4bdc46e04ea5ebaa5', NULL, NULL, NULL, NULL, '-1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:59:48', NULL, '2024-08-09 10:37:55', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('c3729b4006104381b6a32f3539449e94', NULL, NULL, NULL, NULL, ' Copy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:16:38', NULL, '2024-08-09 10:36:05', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('c4a1380134a54d3cb1268bc0a00c1d7f', '/apidev/api/getDownMenuTreeList', NULL, 'cn.apidev.api.ctrl.ApiController', NULL, '123', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-07 18:13:06', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('c4c25f893f3c4b77b1d778625664b805', NULL, NULL, NULL, NULL, '1223', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-06 21:04:50', NULL, '2024-08-09 17:06:59', 'menu', '79af089168c04a299c214d86bf700e09', 0);
INSERT INTO `apidev_api` VALUES ('c74e06ef0b6744368aebb8f3a19abaff', '/apidev/api/debug', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/debug', '/apidev/api/debug（未调试）', NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-07 17:55:41', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('cb18235781f74be19665165775e5d23a', '/apidev/api/sync', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/sync', '同步接口2 Copy', NULL, 'PUT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:01:06', NULL, '2024-08-07 00:14:31', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('cd7d820535884bb99a2c6688c043b0f5', NULL, NULL, NULL, NULL, '功能管理', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:49:13', NULL, NULL, 'menu', 'ed8b09418dbd4587bc35af7176f3fa1c', 0);
INSERT INTO `apidev_api` VALUES ('d3a5d0fe2b034363bd6484b1c1d7ebba', '/apidev/api/getTreeList', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/getTreeList', '/apidev/api/gettreelist（未调试）', NULL, 'POST', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-10 13:28:37', 'api', '12312353', 0);
INSERT INTO `apidev_api` VALUES ('d9b0117b4ae14915af7277607b115b86', NULL, NULL, NULL, NULL, 'ces14', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 00:08:30', NULL, '2024-08-07 11:20:39', 'menu', '70428cac705e4cc8a99bdd4f0e3709bd', 0);
INSERT INTO `apidev_api` VALUES ('dfa7f79ed2aa471693cd6083292adf80', '/apidev/api/1231', NULL, NULL, NULL, NULL, NULL, 'GET', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 18:16:38', NULL, '2024-08-07 18:20:57', 'api', 'a591fc48e7dc4d7eac1f25e1fca9eb15', 2);
INSERT INTO `apidev_api` VALUES ('e090a62f8047425f99d9fd3ca87c20ba', NULL, NULL, NULL, NULL, '-1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:01:50', NULL, '2024-08-09 11:25:58', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('e6efb3349de84326bd2af740df231265', '/apidev/api/addMenu', NULL, 'cn.apidev.api.ctrl.ApiController', NULL, 'null Copy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 17:52:56', NULL, '2024-08-10 13:34:16', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('ebb3e636b301424f81055499c657afd9', '/apidev/api/sync', NULL, 'cn.apidev.api.ctrl.ApiController', '/apidev/api/sync', '同步接口2', NULL, 'PUT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-09 10:23:25', 'api', '-1', 0);
INSERT INTO `apidev_api` VALUES ('ed8b09418dbd4587bc35af7176f3fa1c', NULL, NULL, NULL, NULL, '系统管理 Copy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:49:13', NULL, '2024-08-07 10:47:05', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('fab5cc538a2945b987f869fe4e0cf434', NULL, NULL, NULL, NULL, '特斯太1 Copy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:19:57', NULL, '2024-08-10 16:21:42', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('fcda89dcb04f462ca41a653ef6b0e352', NULL, NULL, NULL, NULL, '2112', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, '2024-08-07 19:19:34', NULL, '2024-08-10 14:29:51', 'menu', '-1', 0);
INSERT INTO `apidev_api` VALUES ('wqewq', '/apidev/api/list', NULL, NULL, NULL, '/apidev/api/list', '/apidev/api/list', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, '2024-08-10 14:30:12', 'link', '-2', 0);

-- ----------------------------
-- Table structure for apidev_share
-- ----------------------------
DROP TABLE IF EXISTS `apidev_share`;
CREATE TABLE `apidev_share`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `visit_type` int NULL DEFAULT 0 COMMENT '访问类型（0：公开访问，1：密码访问）',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问密码',
  `expire_time` datetime NULL DEFAULT NULL COMMENT '到期时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分享记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apidev_share
-- ----------------------------

-- ----------------------------
-- Table structure for apidev_share_api
-- ----------------------------
DROP TABLE IF EXISTS `apidev_share_api`;
CREATE TABLE `apidev_share_api`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `api_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接口id',
  `share_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分享id',
  `sort` int NOT NULL DEFAULT 1 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分享文档关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of apidev_share_api
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
