/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 09/11/2019 22:12:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int(11) NOT NULL,
                        `name` varchar(10) NOT NULL,
                        `age` int(11) NOT NULL,
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (10001, 'test', 22);
INSERT INTO `user` VALUES (10008, 'test88', 88);
INSERT INTO `user` VALUES (30001, 'smile', 22);
INSERT INTO `user` VALUES (100011, 'test101', 101);
INSERT INTO `user` VALUES (100012, 'test102', 102);
INSERT INTO `user` VALUES (100013, '111', 22);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
