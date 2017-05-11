/*
Navicat MySQL Data Transfer

Source Server         : 133.16.157.33
Source Server Version : 50619
Source Host           : 133.16.157.33:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50619
File Encoding         : 65001

Date: 2017-05-11 11:12:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for demo
-- ----------------------------
DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo` (
  `id` varchar(36) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of demo
-- ----------------------------
INSERT INTO `demo` VALUES ('1', '王朋飞');

-- ----------------------------
-- Table structure for demo_info
-- ----------------------------
DROP TABLE IF EXISTS `demo_info`;
CREATE TABLE `demo_info` (
  `id` varchar(36) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of demo_info
-- ----------------------------
INSERT INTO `demo_info` VALUES ('1', '男');

-- ----------------------------
-- Table structure for fb_authority
-- ----------------------------
DROP TABLE IF EXISTS `fb_authority`;
CREATE TABLE `fb_authority` (
  `id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of fb_authority
-- ----------------------------
INSERT INTO `fb_authority` VALUES ('1', 'ROLE_USER');
INSERT INTO `fb_authority` VALUES ('2', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for fb_optlog
-- ----------------------------
DROP TABLE IF EXISTS `fb_optlog`;
CREATE TABLE `fb_optlog` (
  `id` varchar(36) NOT NULL,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Table structure for fb_user
-- ----------------------------
DROP TABLE IF EXISTS `fb_user`;
CREATE TABLE `fb_user` (
  `id` varchar(36) NOT NULL,
  `email` varchar(50) NOT NULL,
  `enabled` int(1) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastpasswordresetdate` datetime NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of fb_user
-- ----------------------------
INSERT INTO `fb_user` VALUES ('1', 'admin@admin.com', '1', 'admin', '2017-03-27 01:35:10', 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin');
INSERT INTO `fb_user` VALUES ('2', 'enabled@user.com', '1', 'user', '2017-03-27 01:35:26', 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user');
INSERT INTO `fb_user` VALUES ('3', 'disabled@user.com', '0', 'user', '2017-03-27 01:35:51', 'user', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'disabled');

-- ----------------------------
-- Table structure for fb_user_authority
-- ----------------------------
DROP TABLE IF EXISTS `fb_user_authority`;
CREATE TABLE `fb_user_authority` (
  `user_id` varchar(36) NOT NULL,
  `authority_id` varchar(36) NOT NULL,
  KEY `fk_fb_user_id` (`user_id`),
  KEY `fk_fb_authority_id` (`authority_id`),
  CONSTRAINT `fk_fb_authority_id` FOREIGN KEY (`authority_id`) REFERENCES `fb_authority` (`id`),
  CONSTRAINT `fk_fb_user_id` FOREIGN KEY (`user_id`) REFERENCES `fb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of fb_user_authority
-- ----------------------------
INSERT INTO `fb_user_authority` VALUES ('1', '1');
INSERT INTO `fb_user_authority` VALUES ('1', '2');
INSERT INTO `fb_user_authority` VALUES ('2', '1');
INSERT INTO `fb_user_authority` VALUES ('3', '1');
