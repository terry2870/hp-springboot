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
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- 正在导出表  test.sys_menu 的数据：~20 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `menu_name`, `menu_url`, `parent_menu_id`, `sort_number`, `status`, `menu_type`, `button_id`, `icon_name`, `target`, `extra_url`, `create_time`, `update_time`, `create_user_id`) VALUES
	(1, '系统管理', '', 0, 2, 1, 1, '', '', '', '', 0, 0, 0),
	(2, '用户管理', '/SysUserController/sysUserList', 1, 1, 1, 2, '', 'person-fill', '', 'queryAllSysUser', 0, 1473785365, 0),
	(3, '角色管理', '/SysRoleController/sysRoleList', 1, 2, 1, 2, '', 'star-fill', '', 'queryAllSysRole', 0, 0, 0),
	(4, '菜单管理', '/SysMenuController/sysMenuList', 1, 3, 1, 2, '', 'list', '', '', 0, 0, 0),
	(5, '查询角色列表', '', 3, 1, 1, 3, '', '', '', 'queryAllSysRole', 0, 1473870231, 0),
	(6, '新增角色', '', 3, 2, 1, 3, 'sysRoleAddBtn', '', '', 'querySysRoleById', 0, 1473870264, 0),
	(9, '修改角色', '', 3, 3, 1, 3, 'sysRoleEditBtn', '', '', 'querySysRoleById', 0, 1473783724, 0),
	(10, '保存角色', '', 3, 4, 1, 3, 'sysRoleSaveBtn', '', '', 'saveSysRole', 0, 1473443318, 0),
	(11, '删除角色', '', 3, 5, 1, 3, 'sysRoleDelBtn', '', '', 'deleteSysRole', 0, 1473443347, 0),
	(12, '查询角色权限', '', 3, 6, 1, 3, 'sysRoleMenuViewBtn', '', '', 'queryAllSysMenu,selectMenuByRoleId', 0, 1473608555, 0),
	(13, '保存角色权限', '', 3, 7, 1, 3, 'sysRoleMenuSaveBtn', '', '', 'saveSysRoleMenu', 0, 1473608585, 0),
	(14, '查询用户列表', '', 2, 1, 1, 3, '', '', '', '/SysUserController/querySysUserPageList', 0, 1473787183, 0),
	(15, '新增用户', '', 2, 2, 1, 3, 'sysUserAddBtn', '', '', 'queryAllSysRole,selectRoleByUserId,querySysUserById', 0, 1473784520, 0),
	(16, '修改用户', '', 2, 3, 1, 3, 'sysUserEditBtn', '', '', 'queryAllSysRole,selectRoleByUserId,querySysUserById', 0, 1473442993, 0),
	(17, '保存用户', '', 2, 4, 1, 3, 'sysUserSaveBtn', '', '', 'saveSysUser', 0, 1473917939, 0),
	(18, '删除用户', '', 2, 5, 1, 3, 'sysUserDelBtn', '', '', 'deleteSysUser', 0, 1534822291, 0),
	(43, '修改密码', '/SysUserController/modifyPwdPage', 1, 5, 1, 2, '', '', '', 'modifyPwd', 0, 1534755226, 0),
	(54, '查看用户详情', '', 2, 6, 1, 3, 'sysUserViewBtn', '', '', 'queryAllSysRole,selectRoleByUserId,querySysUserById', 0, 1473785237, 0),
	(55, '查看角色详情', '', 3, 8, 1, 3, 'sysRoleViewBtn', '', '', 'querySysRoleById', 0, 1473911016, 0),
	(106, '区域管理1', '/jsp/sysManage/sysRegion/sysRegionList.jsp', 1, 5, 1, 2, '', '', '', 'sysRegionList.jsp,sysRegionRight.jsp,queryAllRegion.do,deleteSysregion.do,saveSysRegion.do', 1477573845, 1477573906, 1),
	(107, 'test', '', 0, 2, 1, 1, '', '', '', '', 0, 0, 0),
	(108, '测试菜单', '', 107, 1, 1, 2, '', '', '', '', 0, 0, 0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- 导出  表 test.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_info` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色描述',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '角色状态',
  `create_user_id` int NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `create_time` int NOT NULL DEFAULT '0' COMMENT '创建日期',
  `update_time` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- 正在导出表  test.sys_role 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `role_name`, `role_info`, `status`, `create_user_id`, `create_time`, `update_time`) VALUES
	(1, '用户角色', '角色13333adasda', 1, 0, 0, 1534496511),
	(3, '角色2', '', 1, 0, 0, 1534491593),
	(4, '角色3', 'asdasdasd', 1, 1, 1534491605, 1534491625),
	(5, 'hah ddd', 'asdasd', 1, 2, 1534502304, 1534502318);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- 导出  表 test.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int NOT NULL DEFAULT '0' COMMENT '角色ID',
  `menu_id` int NOT NULL DEFAULT '0' COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK` (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='系统角色菜单关联表';

-- 正在导出表  test.sys_role_menu 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(27, 1, 0),
	(26, 1, 1),
	(16, 1, 2),
	(15, 1, 3),
	(28, 1, 14),
	(25, 5, 106);
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
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '用户状态（1-正常；2-已删除；3-无效）',
  `create_user_id` int NOT NULL DEFAULT '0' COMMENT '创建者ID',
  `last_login_time` int NOT NULL DEFAULT '0' COMMENT '最后一次登录时间',
  `create_time` int NOT NULL DEFAULT '0' COMMENT '创建时间',
  `update_time` int NOT NULL DEFAULT '0' COMMENT '更新时间',
  `identity` tinyint NOT NULL DEFAULT '0' COMMENT '用户身份（1-超级管理员；2-店长；3-店员）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`nick_name`) USING BTREE,
  UNIQUE KEY `login_name` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- 正在导出表  test.sys_user 的数据：~25 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `nick_name`, `login_name`, `login_pwd`, `mobile`, `phone_number`, `head_icon`, `address`, `email`, `status`, `create_user_id`, `last_login_time`, `create_time`, `update_time`, `identity`) VALUES
	(2, '用户13333', 'user1', '$2a$10$4rYNtYlYIF5ZQ2oCbc51Q.GlUz.ttD.TiuO7APHi6hdp8D/gpBlNW', '13951882433', '025-84661234', 'http://t.cn/RCzsdCq', '江苏南京', 'terry@163.com', 1, 0, 1536507741, 1536507741, 1534496533, 2),
	(3, '用户2', 'user2', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 1556507741, 1534495647, 3),
	(4, '用户3', 'user3', '21232f297a57a5a743894a0e4a801fc3', '3', '', '', '', '', 1, 0, 0, 0, 0, 3),
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
	(19, '用户18', 'user18', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(20, '用户19', 'user19', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(21, '用户20', 'user20', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(22, '用户21', 'user21', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(23, '用户22', 'user22', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(24, '用户23', 'user23', '21232f297a57a5a743894a0e4a801fc3', '', '', '', '', '', 1, 0, 0, 0, 0, 3),
	(26, '哈哈', 'hhhh', 'e10adc3949ba59abbe56e057f20f883e', '13951882344', '025-22323323', '', '阿克苏建档立卡教室里的空间爱丽丝', 'www@163.com', 1, 1, 0, 1534495933, 1534495933, 3),
	(27, 'dasdasd', 'aaaa', 'e10adc3949ba59abbe56e057f20f883e', '13951882555', '', '', '', '', 0, 2, 0, 1534497864, 1534497891, 2);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- 导出  表 test.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_id` int NOT NULL DEFAULT '0' COMMENT '角色id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK` (`user_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- 正在导出表  test.sys_user_role 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(17, 2, 1),
	(11, 3, 4),
	(12, 25, 1),
	(13, 26, 3),
	(19, 27, 4);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

-- 导出  表 test.tbl_test 结构
CREATE TABLE IF NOT EXISTS `tbl_test` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `age` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IDX_name` (`name`),
  KEY `IDX_age` (`age`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='测试表';

-- 正在导出表  test.tbl_test 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `tbl_test` DISABLE KEYS */;
INSERT INTO `tbl_test` (`id`, `name`, `age`) VALUES
	(1, 'assdas', 3),
	(2, 'assdas123', 3);
/*!40000 ALTER TABLE `tbl_test` ENABLE KEYS */;

-- 导出  表 test.test_table 结构
CREATE TABLE IF NOT EXISTS `test_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `simplified` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `test_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- 正在导出表  test.test_table 的数据：~13 rows (大约)
/*!40000 ALTER TABLE `test_table` DISABLE KEYS */;
INSERT INTO `test_table` (`id`, `title`, `simplified`, `test_name`) VALUES
	(7, 'title6', 'simplified6', 'testName6'),
	(8, 'title7', 'simplified7', 'testName7'),
	(11, 'title10', 'simplified10', 'testName10'),
	(12, 'title11', 'simplified11', 'testName11'),
	(13, 'title12', 'simplified12', 'testName12'),
	(14, 'title13', 'simplified13', 'testName13'),
	(16, 'title15', 'simplified15', 'testName15'),
	(17, 'title16', 'simplified16', 'testName16'),
	(18, 'title17', 'simplified17', 'testName17'),
	(19, 'title18', 'simplified18', 'testName18'),
	(20, 'title19', 'simplified19', 'testName19');
/*!40000 ALTER TABLE `test_table` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
