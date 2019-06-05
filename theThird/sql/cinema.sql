/*
 Navicat MySQL Data Transfer

 Source Server         : NMSL
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : cinema

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 05/06/2019 09:01:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `a_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `end_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `coupon_id` int(11) NULL DEFAULT NULL,
  `start_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (2, '春季外卖节', '春季外卖节', '2019-04-23 17:55:59', 5, '2019-04-20 17:55:59');
INSERT INTO `activity` VALUES (3, '春季外卖节', '春季外卖节', '2019-04-23 17:55:59', 6, '2019-04-20 17:55:59');
INSERT INTO `activity` VALUES (4, '测试活动', '测试活动', '2019-04-26 16:00:00', 8, '2019-04-20 16:00:00');

-- ----------------------------
-- Table structure for activity_movie
-- ----------------------------
DROP TABLE IF EXISTS `activity_movie`;
CREATE TABLE `activity_movie`  (
  `activity_id` int(11) NULL DEFAULT NULL,
  `movie_id` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity_movie
-- ----------------------------
INSERT INTO `activity_movie` VALUES (2, 10);
INSERT INTO `activity_movie` VALUES (2, 11);
INSERT INTO `activity_movie` VALUES (2, 16);
INSERT INTO `activity_movie` VALUES (4, 10);

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `coupon`;
CREATE TABLE `coupon`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `target_amount` float NULL DEFAULT NULL,
  `discount_amount` float NULL DEFAULT NULL,
  `start_time` timestamp(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `end_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon
-- ----------------------------
INSERT INTO `coupon` VALUES (1, '测试优惠券', '春季电影节', 20, 5, '2019-04-20 17:47:54', '2019-04-23 17:47:59');
INSERT INTO `coupon` VALUES (5, '测试优惠券', '品质联盟', 30, 4, '2019-04-20 21:14:46', '2019-04-24 21:14:51');
INSERT INTO `coupon` VALUES (6, '春节电影节优惠券', '电影节优惠券', 50, 10, '2019-04-20 21:15:11', '2019-04-21 21:14:56');
INSERT INTO `coupon` VALUES (8, '测试优惠券', '123', 100, 99, '2019-04-20 16:00:00', '2019-04-26 16:00:00');
INSERT INTO `coupon` VALUES (9, '无门槛优惠券', 'liuqin', 0, 50, '2019-06-02 21:20:10', '2020-12-31 23:59:59');

-- ----------------------------
-- Table structure for coupon_user
-- ----------------------------
DROP TABLE IF EXISTS `coupon_user`;
CREATE TABLE `coupon_user`  (
  `coupon_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coupon_user
-- ----------------------------
INSERT INTO `coupon_user` VALUES (8, 15);
INSERT INTO `coupon_user` VALUES (5, 15);
INSERT INTO `coupon_user` VALUES (8, 15);
INSERT INTO `coupon_user` VALUES (6, 15);
INSERT INTO `coupon_user` VALUES (5, 15);
INSERT INTO `coupon_user` VALUES (8, 15);
INSERT INTO `coupon_user` VALUES (6, 15);

-- ----------------------------
-- Table structure for hall
-- ----------------------------
DROP TABLE IF EXISTS `hall`;
CREATE TABLE `hall`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `column` int(11) NULL DEFAULT NULL,
  `row` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hall
-- ----------------------------
INSERT INTO `hall` VALUES (1, '1号厅', 10, 5);
INSERT INTO `hall` VALUES (2, '2号厅', 12, 8);

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poster_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `director` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `screen_writer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `starring` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `language` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `length` int(11) NOT NULL,
  `start_date` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `status` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie
-- ----------------------------
INSERT INTO `movie` VALUES (10, 'http://n.sinaimg.cn/translate/640/w600h840/20190312/ampL-hufnxfm4278816.jpg', '大森贵弘 /伊藤秀樹', '', '神谷浩史 /井上和彦 /高良健吾 /小林沙苗 /泽城美雪', '动画', NULL, NULL, 120, '2019-04-14 14:54:31', '夏目友人帐', '在人与妖怪之间过着忙碌日子的夏目，偶然与以前的同学结城重逢，由此回忆起了被妖怪缠身的苦涩记忆。此时，夏目认识了在归还名字的妖怪记忆中出现的女性·津村容莉枝。和玲子相识的她，现在和独子椋雄一同过着平稳的生活。夏目通过与他们的交流，心境也变得平和。但这对母子居住的城镇，却似乎潜伏着神秘的妖怪。在调查此事归来后，寄生于猫咪老师身体的“妖之种”，在藤原家的庭院中，一夜之间就长成树结出果实。而吃掉了与自己形状相似果实的猫咪老师，竟然分裂成了3个', 0);
INSERT INTO `movie` VALUES (11, '', '安娜·波顿', NULL, '布利·拉尔森', '动作/冒险/科幻', NULL, NULL, 120, '2019-04-16 14:55:31', '惊奇队长', '漫画中的初代惊奇女士曾经是一名美国空军均情报局探员，暗恋惊奇先生。。。', 0);
INSERT INTO `movie` VALUES (12, '', '1', NULL, '1', '1', NULL, NULL, 120, '2019-04-16 14:57:31', '1', '1', 0);
INSERT INTO `movie` VALUES (13, '2', '2', NULL, '2', '2', NULL, NULL, 120, '2019-04-16 14:52:31', '2', '2', 0);
INSERT INTO `movie` VALUES (14, '', '2', NULL, '2', '2', NULL, NULL, 120, '2019-04-18 13:23:15', '2', '2', 1);
INSERT INTO `movie` VALUES (15, '1', '1', '1', '1', '1', '1', '1', 111, '2019-04-16 15:00:24', 'nnmm,,,', '1', 0);
INSERT INTO `movie` VALUES (16, 'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549523952.webp', '林孝谦', 'abcˆ', '陈意涵', '爱情', '大陆', NULL, 123, '2019-04-18 13:23:15', '比悲伤更悲伤的故事', '唱片制作人张哲凯(刘以豪)和王牌作词人宋媛媛(陈意涵)相依为命，两人自幼身世坎坷只有彼此为伴，他们是亲人、是朋友，也彷佛是命中注定的另一半。父亲罹患遗传重症而被母亲抛弃的哲凯，深怕自己随时会发病不久人世，始终没有跨出友谊的界线对媛媛展露爱意。眼见哲凯的病情加重，他暗自决定用剩余的生命完成他们之间的终曲，再为媛媛找个可以托付一生的好男人。这时，事业有 成温柔体贴的医生(张书豪)适时的出现让他成为照顾媛媛的最佳人选，二人按部就班发展着关系。一切看似都在哲凯的计划下进行。然而，故事远比这里所写更要悲伤......', 1);

-- ----------------------------
-- Table structure for movie_like
-- ----------------------------
DROP TABLE IF EXISTS `movie_like`;
CREATE TABLE `movie_like`  (
  `movie_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `like_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`movie_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie_like
-- ----------------------------
INSERT INTO `movie_like` VALUES (10, 12, '2019-03-25 02:40:19');
INSERT INTO `movie_like` VALUES (11, 1, '2019-03-22 09:38:12');
INSERT INTO `movie_like` VALUES (11, 2, '2019-03-23 09:38:12');
INSERT INTO `movie_like` VALUES (11, 3, '2019-03-22 08:38:12');
INSERT INTO `movie_like` VALUES (12, 1, '2019-03-23 09:48:46');
INSERT INTO `movie_like` VALUES (12, 3, '2019-03-25 06:36:22');
INSERT INTO `movie_like` VALUES (14, 1, '2019-03-23 09:38:12');
INSERT INTO `movie_like` VALUES (16, 12, '2019-03-23 15:27:48');

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hall_id` int(11) NOT NULL,
  `movie_id` int(11) NOT NULL,
  `start_time` timestamp(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `end_time` timestamp(0) NOT NULL,
  `fare` double NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES (20, 1, 12, '2019-04-13 17:00:00', '2019-04-13 18:00:00', 20.5);
INSERT INTO `schedule` VALUES (21, 1, 10, '2019-04-11 12:00:00', '2019-04-11 13:00:00', 90);
INSERT INTO `schedule` VALUES (27, 1, 11, '2019-04-17 18:01:00', '2019-04-17 20:01:00', 20.5);
INSERT INTO `schedule` VALUES (28, 1, 11, '2019-04-19 16:00:00', '2019-04-19 18:00:00', 20.5);
INSERT INTO `schedule` VALUES (30, 1, 11, '2019-04-18 18:01:00', '2019-04-18 20:01:00', 20.5);
INSERT INTO `schedule` VALUES (31, 1, 11, '2019-04-12 16:00:00', '2019-04-12 18:00:00', 20.5);
INSERT INTO `schedule` VALUES (32, 1, 11, '2019-04-12 20:00:00', '2019-04-12 22:00:00', 20.5);
INSERT INTO `schedule` VALUES (37, 1, 11, '2019-04-15 00:00:00', '2019-04-15 02:00:00', 20.5);
INSERT INTO `schedule` VALUES (38, 1, 11, '2019-04-14 17:00:00', '2019-04-14 19:00:00', 20.5);
INSERT INTO `schedule` VALUES (40, 1, 10, '2019-04-10 16:00:00', '2019-04-10 18:00:00', 20.5);
INSERT INTO `schedule` VALUES (41, 1, 11, '2019-04-10 19:00:00', '2019-04-10 21:00:00', 20.5);
INSERT INTO `schedule` VALUES (42, 1, 11, '2019-04-10 22:00:00', '2019-04-11 00:00:00', 20.5);
INSERT INTO `schedule` VALUES (43, 1, 10, '2019-04-11 01:00:00', '2019-04-11 03:00:00', 20.5);
INSERT INTO `schedule` VALUES (44, 2, 10, '2019-04-11 01:00:00', '2019-04-11 03:00:00', 20.5);
INSERT INTO `schedule` VALUES (45, 2, 10, '2019-04-10 22:00:00', '2019-04-11 00:00:00', 20.5);
INSERT INTO `schedule` VALUES (46, 2, 11, '2019-04-10 19:00:00', '2019-04-10 21:00:00', 20.5);
INSERT INTO `schedule` VALUES (47, 2, 11, '2019-04-10 16:00:00', '2019-04-10 18:00:00', 20.5);
INSERT INTO `schedule` VALUES (48, 2, 10, '2019-04-11 13:00:00', '2019-04-11 15:59:00', 20.5);
INSERT INTO `schedule` VALUES (50, 1, 10, '2019-04-15 16:00:00', '2019-04-15 19:00:00', 2);
INSERT INTO `schedule` VALUES (51, 1, 10, '2019-04-17 05:00:00', '2019-04-17 07:00:00', 9);
INSERT INTO `schedule` VALUES (52, 1, 10, '2019-04-18 05:00:00', '2019-04-18 07:00:00', 9);
INSERT INTO `schedule` VALUES (53, 1, 16, '2019-04-19 07:00:00', '2019-04-19 10:00:00', 9);
INSERT INTO `schedule` VALUES (54, 1, 16, '2019-04-16 19:00:00', '2019-04-16 22:00:00', 9);
INSERT INTO `schedule` VALUES (55, 1, 15, '2019-04-17 23:00:00', '2019-04-18 01:00:00', 9);
INSERT INTO `schedule` VALUES (56, 2, 10, '2019-04-19 13:00:00', '2019-04-19 15:59:00', 20.5);
INSERT INTO `schedule` VALUES (57, 2, 10, '2019-04-20 13:00:00', '2019-04-20 15:59:00', 20.5);
INSERT INTO `schedule` VALUES (58, 2, 10, '2019-04-21 13:00:00', '2019-04-21 15:59:00', 20.5);
INSERT INTO `schedule` VALUES (61, 1, 13, '2019-04-20 11:00:00', '2019-04-20 13:00:00', 25);
INSERT INTO `schedule` VALUES (62, 1, 11, '2019-04-20 08:00:00', '2019-04-20 10:00:00', 25);
INSERT INTO `schedule` VALUES (63, 2, 15, '2019-04-20 16:01:30', '2019-04-21 05:30:00', 30);
INSERT INTO `schedule` VALUES (64, 1, 16, '2019-04-22 02:00:00', '2019-04-22 05:30:00', 30);
INSERT INTO `schedule` VALUES (65, 1, 10, '2019-04-23 02:00:00', '2019-04-23 05:30:00', 30);
INSERT INTO `schedule` VALUES (66, 2, 13, '2019-04-21 07:31:29', '2019-04-16 15:59:00', 20.5);
INSERT INTO `schedule` VALUES (67, 2, 10, '2019-04-25 13:00:00', '2019-04-25 15:59:00', 20.5);
INSERT INTO `schedule` VALUES (68, 2, 10, '2019-04-26 13:00:00', '2019-04-26 15:59:00', 20.5);

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `schedule_id` int(11) NULL DEFAULT NULL,
  `column_index` int(11) NULL DEFAULT NULL,
  `row_index` int(11) NULL DEFAULT NULL,
  `state` tinyint(4) NULL DEFAULT NULL,
  `time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `order_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 63 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ticket
-- ----------------------------
INSERT INTO `ticket` VALUES (1, 12, 50, 5, 3, 2, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (2, 12, 50, 5, 3, 2, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (3, 12, 50, 5, 3, 2, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (4, 12, 50, 5, 3, 2, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (5, 12, 50, 5, 3, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (6, 15, 50, 4, 3, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (15, 15, 58, 0, 0, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (16, 15, 58, 2, 0, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (17, 15, 58, 1, 1, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (18, 15, 58, 11, 7, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (19, 13, 50, 4, 2, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (20, 15, 66, 3, 2, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (21, 12, 50, 1, 1, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (22, 13, 50, 4, 3, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (23, 15, 50, 2, 2, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (24, 15, 58, 0, 7, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (25, 15, 58, 5, 4, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (26, 15, 58, 6, 4, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (27, 15, 58, 6, 2, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (28, 15, 58, 7, 2, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (29, 15, 58, 0, 4, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (30, 15, 58, 0, 3, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (31, 15, 58, 0, 2, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (32, 15, 58, 10, 0, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (33, 15, 58, 11, 0, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (34, 15, 58, 8, 0, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (35, 15, 58, 9, 0, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (36, 15, 58, 5, 0, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (37, 15, 58, 6, 0, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (38, 15, 58, 6, 7, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (39, 15, 58, 7, 7, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (40, 15, 58, 8, 7, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (41, 15, 58, 11, 4, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (42, 15, 58, 11, 5, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (43, 15, 58, 9, 6, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (44, 15, 58, 10, 6, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (45, 15, 58, 11, 6, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (46, 15, 58, 3, 5, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (47, 15, 58, 4, 5, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (48, 15, 58, 5, 5, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (49, 15, 58, 11, 2, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (50, 15, 58, 11, 3, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (51, 15, 58, 9, 4, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (52, 15, 58, 9, 3, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (53, 15, 58, 10, 3, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (54, 15, 65, 7, 4, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (55, 15, 65, 8, 4, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (56, 15, 65, 9, 4, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (57, 15, 65, 7, 3, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (58, 15, 65, 8, 3, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (59, 15, 65, 9, 3, 0, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (60, 15, 65, 0, 0, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (61, 15, 65, 0, 1, 1, '2019-04-23 13:50:52', NULL);
INSERT INTO `ticket` VALUES (62, 15, 65, 0, 2, 1, '2019-04-23 13:50:52', NULL);

-- ----------------------------
-- Table structure for ticket_refund
-- ----------------------------
DROP TABLE IF EXISTS `ticket_refund`;
CREATE TABLE `ticket_refund`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rate` float NOT NULL,
  `limitHours` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_uindex`(`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'testname', '123456');
INSERT INTO `user` VALUES (3, 'test', '123456');
INSERT INTO `user` VALUES (5, 'test1', '123456');
INSERT INTO `user` VALUES (7, 'test121', '123456');
INSERT INTO `user` VALUES (8, 'root', '123456');
INSERT INTO `user` VALUES (10, 'roottt', '123123');
INSERT INTO `user` VALUES (12, 'zhourui', '123456');
INSERT INTO `user` VALUES (13, 'abc123', 'abc123');
INSERT INTO `user` VALUES (15, 'dd', '123');

-- ----------------------------
-- Table structure for view
-- ----------------------------
DROP TABLE IF EXISTS `view`;
CREATE TABLE `view`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of view
-- ----------------------------
INSERT INTO `view` VALUES (1, 7);

-- ----------------------------
-- Table structure for vip_card
-- ----------------------------
DROP TABLE IF EXISTS `vip_card`;
CREATE TABLE `vip_card`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `balance` float NULL DEFAULT NULL,
  `join_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `vip_card_user_id_uindex`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip_card
-- ----------------------------
INSERT INTO `vip_card` VALUES (1, 15, 375, '2019-04-21 13:54:38');
INSERT INTO `vip_card` VALUES (2, 12, 660, '2019-04-17 18:47:42');

-- ----------------------------
-- Table structure for vip_charge_history
-- ----------------------------
DROP TABLE IF EXISTS `vip_charge_history`;
CREATE TABLE `vip_charge_history`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `charge` double NULL DEFAULT NULL,
  `balance` double NULL DEFAULT NULL,
  `date` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip_charge_history
-- ----------------------------
INSERT INTO `vip_charge_history` VALUES (1, 15, 20, 375, '2019-06-02 21:23:57');

-- ----------------------------
-- Table structure for vip_info
-- ----------------------------
DROP TABLE IF EXISTS `vip_info`;
CREATE TABLE `vip_info`  (
  `price` double(11, 0) NOT NULL,
  `charge` double(255, 0) NULL DEFAULT NULL,
  `bonus` double(255, 0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vip_info
-- ----------------------------
INSERT INTO `vip_info` VALUES (25, 300, 20);

SET FOREIGN_KEY_CHECKS = 1;
