-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.22 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  11.1.0.6116
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 导出 test 的数据库结构
CREATE DATABASE IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `test`;

-- 导出  函数 test.getParentsMenuIdByMenuId 结构
DELIMITER //
CREATE FUNCTION `getParentsMenuIdByMenuId`(rootId VARCHAR(5000)) RETURNS varchar(5000) CHARSET utf8
    READS SQL DATA
    COMMENT '向上递归查询所有父菜单节点'
BEGIN

    DECLARE sTemp VARCHAR(5000);

    DECLARE sTempChd VARCHAR(5000);

    SET sTemp = '$';

    SET sTempChd =cast(rootId as CHAR);

      WHILE sTempChd is not null DO

        SET sTemp = concat(sTemp,',',sTempChd);

        SELECT group_concat(parent_menu_id) INTO sTempChd FROM sys_menu where FIND_IN_SET(id,sTempChd)>0;

      END WHILE;

    RETURN sTemp;

  END//
DELIMITER ;

-- 导出  表 test.sys_config 结构
CREATE TABLE IF NOT EXISTS `sys_config` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `config_name` varchar(100) NOT NULL DEFAULT '' COMMENT '配置名称',
  `config_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '配置描述',
  `config_key` varchar(100) NOT NULL DEFAULT '' COMMENT '配置的key',
  `config_value` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '配置的值',
  `config_value_type` tinyint NOT NULL DEFAULT '1' COMMENT '配置数据类型（1-字符串；2-数字；3-日期时间；4-富文本）',
  `config_value_type_desc` varchar(128) NOT NULL DEFAULT '' COMMENT '配置数据类型的详细信息',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态（1-正常；0-无效）',
  `create_user_id` int NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `create_time` int NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `config_key` (`config_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- 正在导出表  test.sys_config 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` (`id`, `config_name`, `config_desc`, `config_key`, `config_value`, `config_value_type`, `config_value_type_desc`, `status`, `create_user_id`, `create_time`, `update_time`) VALUES
	(1, '测试配置数据', 'asdasdasd2', 'test_key', '34411', 2, '', 1, 2, 1614254340, 1614332867);
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;

-- 导出  表 test.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单名称',
  `menu_url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单地址',
  `parent_menu_id` int NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `sort_number` int NOT NULL DEFAULT '0' COMMENT '排序号',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态',
  `menu_type` tinyint NOT NULL DEFAULT '0' COMMENT '菜单类型（1-根节点；2-子结点；3-按钮）',
  `button_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '按钮的ID名称',
  `icon_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标',
  `target` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单指向',
  `extra_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '额外参数',
  `create_time` int NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  `create_user_id` int NOT NULL DEFAULT '0' COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- 正在导出表  test.sys_menu 的数据：~23 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_number`, `status`, `menu_type`, `button_id`, `icon_name`, `target`, `extra_url`, `create_time`, `update_time`, `create_user_id`) VALUES
	(1, '系统管理', '', 0, 1, 1, 1, '', '', '', '', 0, 1613974095, 0),
	(2, '用户管理', '/SysUserController/sysUserList', 1, 1, 1, 2, '', 'person-fill', '', '/SysUserController/querySysUserPageList', 0, 1613965645, 0),
	(3, '角色管理', '/SysRoleController/sysRoleList', 1, 2, 1, 2, '', 'star-fill', '', '/SysRoleController/querySysRolePageList', 0, 0, 0),
	(4, '菜单管理', '/SysMenuController/sysMenuList', 1, 3, 1, 2, '', 'list', '', '', 0, 1613785994, 0),
	(5, '查看用户详情', '', 2, 6, 1, 3, 'viewSysUserBtn', '', '', '/SysUserController/sysUserEdit,/SysUserController/querySysUserById', 0, 1473785237, 0),
	(6, '新增用户', '', 2, 2, 1, 3, 'addSysUserBtn', '', '', '/SysUserController/sysUserEdit,/SysUserController/saveSysUser', 0, 1614246462, 0),
	(7, '修改用户', '', 2, 3, 1, 3, 'editSysUserBtn', '', '', '/SysUserController/sysUserEdit,/SysUserController/querySysUserById,/SysUserController/saveSysUser', 0, 1614246473, 0),
	(9, '删除用户', '', 2, 5, 1, 3, 'delSysUserBtn', '', '', '/SysUserController/deleteSysUser', 0, 1534822291, 0),
	(10, '查看角色详情', '', 3, 8, 1, 3, 'sysRoleViewBtn', '', '', '', 0, 1473911016, 0),
	(11, '新增角色', '', 3, 2, 1, 3, 'addSysRoleBtn', '', '', '/SysRoleController/saveSysRole', 0, 1614246559, 0),
	(12, '修改角色', '', 3, 3, 1, 3, 'editSysRoleBtn', '', '', '/SysRoleController/querySysRoleById,/SysRoleController/saveSysRole', 0, 1614246567, 0),
	(14, '删除角色', '', 3, 5, 1, 3, 'delSysRoleBtn', '', '', '/SysRoleController/deleteSysRole', 0, 1473443347, 0),
	(15, '修改角色权限', '', 3, 6, 1, 3, 'viewSysRoleMenuBtn', '', '', '/SysMenuController/queryAllSysMenu,/SysRoleController/queryMenuByRoleId,/SysRoleController/saveSysRoleMenu', 0, 1614246694, 0),
	(17, '修改密码', '/SysUserController/modifyPwdPage', 1, 5, 1, 2, '', '', '', '/SysUserController/modifyPwd', 0, 1534755226, 0),
	(18, '修改用户状态', '', 2, 7, 1, 3, 'changeSysUserStatusBtn', '', '', '/SysUserController/changeUserStatus', 1614065126, 1614065140, -1),
	(19, '系统配置管理', '/SysConfigController/sysConfigList', 1, 4, 1, 2, '', '', '', '/SysConfigController/querySysConfigPageList', 1614246199, 1614246199, -1),
	(20, '查看系统配置详情', '', 19, 1, 1, 3, 'viewSysConfigBtn', '', '', '/SysConfigController/querySysConfigById', 1614246251, 1614246251, -1),
	(21, '新增系统配置', '', 19, 2, 1, 3, 'addSysConfigBtn', '', '', '/SysConfigController/saveSysConfig', 1614246322, 1614246322, -1),
	(22, '修改系统配置', '', 19, 3, 1, 3, 'editSysConfigBtn', '', '', '/SysConfigController/saveSysConfig,/SysConfigController/querySysConfigById', 1614246365, 1614246365, -1),
	(23, '删除系统配置', '', 19, 4, 1, 3, 'delSysConfigBtn', '', '', '/SysConfigController/deleteSysConfig', 1614246405, 1614246405, -1),
	(24, '修改系统配置状态', '', 19, 5, 1, 3, 'changeSysConfigStatusBtn', '', '', '/SysConfigController/changeSysConfigStatus', 1614246439, 1614246439, -1),
	(25, '查看用户列表', '', 2, 1, 1, 3, 'xx', '', '', '/SysUserController/querySysUserPageList', 1614326839, 1614326839, -1),
	(26, '查看角色列表', '', 3, 1, 1, 3, 'xx', '', '', '/SysRoleController/querySysRolePageList', 1614326869, 1614326869, -1),
	(27, '查看配置列表', '', 19, 1, 1, 3, 'xx', '', '', '/SysConfigController/querySysConfigPageList', 1614326886, 1614326886, -1),
	(28, '测试表', '/TestTableController/testTableList', 1, 6, 1, 2, '', '', '', '/TestTableController/queryTestTablePageList,/TestTableController/saveTestTable,/TestTableController/deleteTestTable,/TestTableController/queryTestTableById,/TestTableController/changeTestTableStatus', 1614589451, 1614589451, -1);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- 导出  表 test.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `role_info` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色描述',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '角色状态',
  `create_user_id` int NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `create_time` int NOT NULL DEFAULT '0' COMMENT '创建日期',
  `update_time` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- 正在导出表  test.sys_role 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `role_name`, `role_info`, `status`, `create_user_id`, `create_time`, `update_time`) VALUES
	(1, '用户角色', '角色13333adasda', 1, 0, 0, 1614325100),
	(3, '初始用户角色', '只提供最基本的菜单，如（修改密码）', 1, 0, 0, 1614073310),
	(4, '角色3', 'asd12345dd', 1, 1, 1534491605, 1613980974),
	(6, '123', 'sdfsdf', 1, 2, 1614045488, 1614045488),
	(7, '啊实打实', 'sss', 1, 2, 1614069632, 1614325149);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- 导出  表 test.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_id` int NOT NULL DEFAULT '0' COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK` (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=201 DEFAULT CHARSET=utf8 COMMENT='系统角色菜单关联表';

-- 正在导出表  test.sys_role_menu 的数据：~18 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(188, 1, 5),
	(185, 1, 6),
	(186, 1, 7),
	(187, 1, 9),
	(194, 1, 10),
	(190, 1, 11),
	(191, 1, 12),
	(192, 1, 14),
	(193, 1, 15),
	(189, 1, 18),
	(195, 1, 20),
	(196, 1, 21),
	(197, 1, 22),
	(198, 1, 23),
	(199, 1, 24),
	(200, 1, 28),
	(183, 3, 17),
	(184, 4, 25);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- 导出  表 test.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nick_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录名',
  `login_pwd` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登录密码',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号码',
  `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '固定电话',
  `head_icon` varchar(100) NOT NULL DEFAULT '' COMMENT '用户头像',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户地址',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'email',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '用户状态（1-正常；0-无效）',
  `create_user_id` int NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `last_login_time` int NOT NULL DEFAULT '0' COMMENT '最后一次登录时间',
  `create_time` int NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  `identity` tinyint NOT NULL DEFAULT '0' COMMENT '用户身份（1-超级管理员；2-店长；3-店员）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`nick_name`) USING BTREE,
  UNIQUE KEY `login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- 正在导出表  test.sys_user 的数据：~18 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `nick_name`, `login_name`, `login_pwd`, `mobile`, `phone_number`, `head_icon`, `address`, `email`, `status`, `create_user_id`, `last_login_time`, `create_time`, `update_time`, `identity`) VALUES
	(2, '用户1', 'user1', '$2a$10$iN2hulrxIDSJgM56TMtAi.C1OfB2fXpF1iBQaWylkjpT7OtCSxhte', '13951882433', '025-84661234', 'http://t.cn/RCzsdCq', '江苏南京123', 'terry@163.com', 1, 0, 1614598254, 1536507741, 1614331901, 2),
	(3, '用户2', 'user2', '$2a$10$iN2hulrxIDSJgM56TMtAi.C1OfB2fXpF1iBQaWylkjpT7OtCSxhte', '1', '', '', '3', '2', 1, 0, 1614330268, 1556507741, 1614332952, 3),
	(4, '用户3', 'user3', '21232f297a57a5a743894a0e4a801fc3', '3', '', '', '', '', 0, 0, 0, 0, 1614330074, 3),
	(5, '用户4', 'user4', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(6, '用户5', 'user5', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(7, '用户6', 'user6', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(8, '用户7', 'user7', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(9, '用户8', 'user8', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(10, '用户91112121', 'user9', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 1536316018, 3),
	(11, '用户10', 'user10', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(12, '用户11', 'user11', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(13, '用户12', 'user12', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(14, '用户13', 'user13', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(15, '用户14', 'user14', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 1534497996, 3),
	(16, '用户15', 'user15', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(17, '用户16', 'user16', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(18, '用户17', 'user17', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 0, 0, 0, 0, 0, 3),
	(28, '2', '1', '$2a$10$x7/FWiiqlbIXQn51h7aa8et/MaI5h4.1mKHdwMteY1K5liKfn/1eq', '3', '', '', '5', '4', 1, 2, 0, 1614078234, 1614078234, 2);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- 导出  表 test.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` int NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 正在导出表  test.sys_user_role 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(38, 2, 1),
	(39, 2, 3),
	(36, 3, 3),
	(37, 3, 4),
	(35, 4, 3),
	(32, 28, 3);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

-- 导出  表 test.test_table 结构
CREATE TABLE IF NOT EXISTS `test_table` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '标题',
  `simplified` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '简体',
  `test_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '测试名称',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '用户状态（1-正常；0-无效）',
  `create_user_id` int NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `create_time` int NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='测试表';

-- 正在导出表  test.test_table 的数据：~11 rows (大约)
/*!40000 ALTER TABLE `test_table` DISABLE KEYS */;
INSERT INTO `test_table` (`id`, `title`, `simplified`, `test_name`, `status`, `create_user_id`, `create_time`, `update_time`) VALUES
	(8, 'title7', 'simplified7', 'testName7', 1, 0, 0, 1614597888),
	(11, 'title10', 'simplified10', 'testName10', 0, 0, 0, 1614597885),
	(12, 'title11', 'simplified11', 'testName11', 0, 0, 0, 1614598285),
	(13, 'title12', 'simplified12', 'testName12', 0, 0, 0, 1614598287),
	(14, 'title13', 'simplified13', 'testName13', 1, 0, 0, 0),
	(16, 'title15', 'simplified15', 'testName15', 1, 0, 0, 0),
	(17, 'title16', 'simplified16', 'testName16', 1, 0, 0, 0),
	(18, 'title17', 'simplified17', 'testName17', 1, 0, 0, 0),
	(19, 'title18', 'simplified18', 'testName18', 1, 0, 0, 0),
	(20, 'title19', 'simplified19', 'testName19', 1, 0, 0, 0);
/*!40000 ALTER TABLE `test_table` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
