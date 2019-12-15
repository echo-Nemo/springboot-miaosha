/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : shopmiaosha

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2019-12-15 15:41:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL DEFAULT '',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `description` varchar(255) NOT NULL DEFAULT '',
  `img_url` varchar(255) NOT NULL DEFAULT '',
  `sales` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('1', 'iphone12', '21123.00', '不错', 'https://i1.mifile.cn/a1/pms_1550642182.7527088!220x220.jpg', '141');
INSERT INTO `item` VALUES ('2', 'iphone13', '12321.00', 'nice', 'https://i1.mifile.cn/a1/pms_1550642182.7527088!220x220.jpg', '138');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `stock` int(10) NOT NULL DEFAULT '0',
  `item_id` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES ('12', '123', '13');
INSERT INTO `item_stock` VALUES ('13', '123', '14');
INSERT INTO `item_stock` VALUES ('14', '123', '49');
INSERT INTO `item_stock` VALUES ('15', '103', '1');
INSERT INTO `item_stock` VALUES ('16', '94', '2');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` varchar(255) NOT NULL,
  `item_id` int(32) NOT NULL DEFAULT '0',
  `user_id` int(32) NOT NULL DEFAULT '0',
  `item_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `amout` int(255) NOT NULL DEFAULT '0',
  `order_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `promo_id` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2019121000002400', '2', '33', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121000002500', '1', '33', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121000002600', '1', '33', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121000002700', '1', '32', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121000002800', '1', '31', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121200002900', '1', '33', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003000', '1', '33', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003100', '2', '31', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003200', '1', '31', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003300', '2', '31', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003400', '1', '30', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003500', '2', '30', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003600', '2', '33', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003700', '2', '32', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003800', '2', '31', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200003900', '2', '32', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200004000', '2', '32', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200004100', '2', '32', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200004200', '2', '32', '12321.00', '1', '12321.00', '0');
INSERT INTO `order_info` VALUES ('2019121200004300', '1', '31', '21123.00', '1', '21123.00', '0');
INSERT INTO `order_info` VALUES ('2019121200004400', '1', '33', '10000.00', '1', '10000.00', '1');
INSERT INTO `order_info` VALUES ('2019121200004500', '1', '32', '10000.00', '1', '10000.00', '1');
INSERT INTO `order_info` VALUES ('2019121200004600', '1', '30', '10000.00', '1', '10000.00', '1');

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(255) NOT NULL DEFAULT '',
  `start_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `item_id` int(11) NOT NULL DEFAULT '0',
  `promo_item_price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `end_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES ('1', 'iphone12', '2019-05-05 14:39:47', '1', '10000.00', '2019-12-22 10:00:00');
INSERT INTO `promo` VALUES ('2', 'iphone13', '2019-12-13 11:11:00', '2', '9000.00', '2019-12-24 23:12:12');

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info` (
  `name` varchar(255) NOT NULL DEFAULT '',
  `current_value` int(11) NOT NULL DEFAULT '0',
  `step` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', '47', '1');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `gender` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '1为男性，2为女性',
  `age` int(11) NOT NULL DEFAULT '0',
  `telphone` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `register_mode` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT 'byphone,bywechat,byalipay',
  `third_party_id` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `telphone_unique_index` (`telphone`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('10', 'czj', '1', '22', '16735627892', 'byphone', '');
INSERT INTO `user_info` VALUES ('11', 'admin', '1', '12', '111', 'byphone', '');
INSERT INTO `user_info` VALUES ('13', '1223', '1', '23', '1111', 'byphone', '');
INSERT INTO `user_info` VALUES ('15', 'admin', '1', '29', '1234', 'byphone', '');
INSERT INTO `user_info` VALUES ('16', '陈经理', '1', '29', '1222', 'byphone', '');
INSERT INTO `user_info` VALUES ('26', 'echo', '1', '12', '14523112322', 'byphone', '');
INSERT INTO `user_info` VALUES ('27', 'alices', '1', '11', '15623451233', 'byphone', '');
INSERT INTO `user_info` VALUES ('28', 'nemo', '1', '11', '15623115111', 'byphone', '');
INSERT INTO `user_info` VALUES ('29', 'jile', '1', '11', '15612331234', 'byphone', '');
INSERT INTO `user_info` VALUES ('30', 'juerl', '1', '15', '15623112314', 'byphone', '');
INSERT INTO `user_info` VALUES ('31', 'echo', '1', '11', '1232221111', 'byphone', '');
INSERT INTO `user_info` VALUES ('32', 'echo', '1', '11', '15623442111', 'byphone', '');
INSERT INTO `user_info` VALUES ('33', 'echo', '1', '11', '16723441234', 'byphone', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `encrpt_password` varchar(128) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES ('6', 'sdsdgskgjsghd', '10');
INSERT INTO `user_password` VALUES ('7', '4QrcOUm6Wau+VuBX8g+IPg==', '0');
INSERT INTO `user_password` VALUES ('8', '86xjyRJy8ZzpfHOXglzBXw==', '0');
INSERT INTO `user_password` VALUES ('9', 'NeLc2+oZUKeikN0MKC2goA==', '15');
INSERT INTO `user_password` VALUES ('10', '4QrcOUm6Wau+VuBX8g+IPg==', '16');
INSERT INTO `user_password` VALUES ('27', 'ICy5YqxZB1uWSwcVLSNLcA==', '27');
INSERT INTO `user_password` VALUES ('28', '4QrcOUm6Wau+VuBX8g+IPg==', '28');
INSERT INTO `user_password` VALUES ('29', '4QrcOUm6Wau+VuBX8g+IPg==', '29');
INSERT INTO `user_password` VALUES ('30', '4QrcOUm6Wau+VuBX8g+IPg==', '30');
INSERT INTO `user_password` VALUES ('31', '4QrcOUm6Wau+VuBX8g+IPg==', '31');
INSERT INTO `user_password` VALUES ('32', '4QrcOUm6Wau+VuBX8g+IPg==', '32');
INSERT INTO `user_password` VALUES ('33', '4QrcOUm6Wau+VuBX8g+IPg==', '33');
