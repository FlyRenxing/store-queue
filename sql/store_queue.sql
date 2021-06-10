/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : store_queue

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 10/06/2021 10:56:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `tyid`   int                                                           NOT NULL AUTO_INCREMENT,
    `tyname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名',
    PRIMARY KEY (`tyid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 8
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category`
VALUES (1, '化妆品');
INSERT INTO `category`
VALUES (2, '零食');
INSERT INTO `category`
VALUES (3, '日用品');
INSERT INTO `category`
VALUES (4, '服装');
INSERT INTO `category`
VALUES (5, '家具');
INSERT INTO `category`
VALUES (6, '水果');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`
(
    `gid`      int                                                           NOT NULL AUTO_INCREMENT,
    `gname`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `price`    decimal(10, 2)                                                NOT NULL COMMENT '价格',
    `category` int                                                           NOT NULL COMMENT '商品分类',
    `total`    int                                                           NOT NULL COMMENT '总数',
    `stock`    int                                                           NOT NULL COMMENT '库存',
    `state`    int                                                           NOT NULL COMMENT '商品状态，0正常，1下架',
    `pic`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NOT NULL COMMENT '商品',
    `details`  longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci     NULL COMMENT '商品详情',
    `remarks`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`gid`) USING BTREE,
    INDEX `type_typeid` (`category`) USING BTREE,
    CONSTRAINT `type_typeid` FOREIGN KEY (`category`) REFERENCES `category` (`tyid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 39
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods`
VALUES (1, 'AKF眼线棕色', 1.99, 1, 100, 41, 0,
        'https://pan.imzdx.top/api/v3/file/get/34/a3dcd7447ffd4020a03febe4f7f7c5cb_800_800.jpg?sign=T3yWip5Bd4JLQpuEnzS5EWmWMFeLWfw3TYn4BK9WMfY%3D%3A0',
        '眼影好啊', '这是个备注');
INSERT INTO `goods`
VALUES (2, '乐事薯片', 7.00, 2, 100, 0, 0,
        'https://pan.imzdx.top/api/v3/file/get/36/u=2912013558,1585633268&fm=26&gp=0.jpg?sign=kLQHInmoMurVYV35rJwABw059G-Whph9mlIfOV9QCko%3D%3A0',
        '薯片妙啊', '这是个备注');
INSERT INTO `goods`
VALUES (4, '日本原装正品MUJI无印良品洗澡刷长柄沐浴刷搓背刷后背搓澡神器 ', 9.50, 3, 100, 38, 0,
        'https://pan.imzdx.top/api/v3/file/get/33/6063285db162c9970.jpg_d250.jpg?sign=RPZxD_LZJi3x7lyoYl2K_3PMCyYn1yHD24L6hJBZWzY%3D%3A0',
        '搓澡神器', '这是个备注');
INSERT INTO `goods`
VALUES (5, '舒肤佳排浊香皂男女家庭洗手洗澡沐浴108g*3红石榴竹炭洗脸皂留香', 6.99, 3, 100, 55, 0,
        'https://pan.imzdx.top/api/v3/file/get/31/5fabf84eee418915.jpg_d250.jpg?sign=y4fVKjuR_HETd2iWN3wkTu9brfnnG5drDPxQdDFPqkQ%3D%3A0',
        '网红香薰红石榴皂下单加送起泡网！', '这是个备注');
INSERT INTO `goods`
VALUES (6, '【EH STORE】2021春装设计感连体裤潮 撞色金属扣牛仔背带裤子女', 155.00, 4, 100, 78, 0,
        'https://pan.imzdx.top/api/v3/file/get/35/O1CN019GRfRv20MTlfJS4kL_!!263816835.jpg?sign=f3KcfNxaCvzl5He1gEKeXAsHz0pO6xhwN2Z6HGRAKQM%3D%3A0',
        '【EH STORE】2021春装设计感连体裤潮 撞色金属扣牛仔背带裤子女', '这是个备注');
INSERT INTO `goods`
VALUES (8, '金磨坊70g大刀肉素肉辣条片怀旧网红休闲小吃零食豆干豆皮小包装', 2.99, 2, 100, 32, 0,
        'https://pan.imzdx.top/api/v3/file/get/37/5ed45ee90dff46345.jpg_d250.jpg?sign=P7WoUu-JvPLxRkCskP3lRFCK1TwPIyEHolrDrY4T2JY%3D%3A0',
        '金磨坊70g大刀肉素肉辣条片怀旧网红休闲小吃零食豆干豆皮小包装', '这是个备注');
INSERT INTO `goods`
VALUES (9, '三只松鼠芒果干60g蜜饯果脯水果干网红休闲办公室零食小吃好吃的', 5.90, 2, 100, 21, 0,
        'https://pan.imzdx.top/api/v3/file/get/44/bf1c154f63505e77.jpg?sign=h2gxwuROPTbj6-ss2U-zAwtj1m8z2QldNu88tLaTAfo%3D%3A0',
        '大块厚切鲜芒 来点香甜滋味', '这是个备注');
INSERT INTO `goods`
VALUES (10, '换鞋凳鞋柜实木现代简约可坐简易门口试穿鞋架鞋凳子多功能 普通款-50长 ', 159.00, 5, 100, 2, 0,
        'https://pan.imzdx.top/api/v3/file/get/39/%E6%8D%A2%E9%9E%8B%E5%87%B3%E9%9E%8B%E6%9F%9C%E5%AE%9E%E6%9C%A8%E7%8E%B0%E4%BB%A3%E7%AE%80%E7%BA%A6%E5%8F%AF%E5%9D%90%E7%AE%80%E6%98%93%E9%97%A8%E5%8F%A3%E8%AF%95%E7%A9%BF%E9%9E%8B%E6%9E%B6%E9%9E%8B%E5%87%B3%E5%AD%90%E5%A4%9A%E5%8A%9F%E8%83%BD%20%E6%99%AE%E9%80%9A%E6%AC%BE-50%E9%95%BF%20.jpg?sign=WAMyw4luRLRDbeurxozZ4-VgiJ9yqI-KwosCnmmHT_g%3D%3A0',
        '送运费险/贴心客服/破损免费补发/系列齐全/全屋搭配一站购齐(部分偏远地区不发货）更多惊喜抢购！', '这是个备注');
INSERT INTO `goods`
VALUES (11, '绿鲜森新鲜巴掌榴莲泰国进口金枕头生鲜水果 14斤装（2-4个） ', 398.90, 6, 100, 55, 0,
        'https://pan.imzdx.top/api/v3/file/get/40/%E7%BB%BF%E9%B2%9C%E6%A3%AE%E6%96%B0%E9%B2%9C%E5%B7%B4%E6%8E%8C%E6%A6%B4%E8%8E%B2%E6%B3%B0%E5%9B%BD%E8%BF%9B%E5%8F%A3%E9%87%91%E6%9E%95%E5%A4%B4%E7%94%9F%E9%B2%9C%E6%B0%B4%E6%9E%9C%2014%E6%96%A4%E8%A3%85%EF%BC%882-4%E4%B8%AA%EF%BC%89.jpg?sign=aZP174o4wGBqTcM4kPq0Lc92RpoHaYIABQGedJ9Ke04%3D%3A0',
        '坏果包赔 ', '这是个备注');
INSERT INTO `goods`
VALUES (12, '土八鲜 越南玉芒大青芒果 新鲜水果当季整箱甜心芒应季芒果 带箱3斤玉芒果 ', 19.90, 6, 100, 62, 0,
        'https://pan.imzdx.top/api/v3/file/get/43/%E5%9C%9F%E5%85%AB%E9%B2%9C%20%E8%B6%8A%E5%8D%97%E7%8E%89%E8%8A%92%E5%A4%A7%E9%9D%92%E8%8A%92%E6%9E%9C%20%E6%96%B0%E9%B2%9C%E6%B0%B4%E6%9E%9C%E5%BD%93%E5%AD%A3%E6%95%B4%E7%AE%B1%E7%94%9C%E5%BF%83%E8%8A%92%E5%BA%94%E5%AD%A3%E8%8A%92%E6%9E%9C%20%E5%B8%A6%E7%AE%B13%E6%96%A4%E7%8E%89%E8%8A%92%E6%9E%9C.jpg?sign=4PhSVMFSGWvqNXlotqL0_eg8wnp5D0aaR0vm2PvECCk%3D%3A0',
        '当季芒果，香甜肉厚；润肺枇杷，当季鲜果', '这是个备注');
INSERT INTO `goods`
VALUES (13, '美果汇 国产大樱桃 水果礼盒 大樱桃 生鲜水果 3斤JJ级', 189.90, 6, 100, 42, 0,
        'https://pan.imzdx.top/api/v3/file/get/41/%E7%BE%8E%E6%9E%9C%E6%B1%87%20%E5%9B%BD%E4%BA%A7%E5%A4%A7%E6%A8%B1%E6%A1%83%20%E6%B0%B4%E6%9E%9C%E7%A4%BC%E7%9B%92%20%E5%A4%A7%E6%A8%B1%E6%A1%83%20%E7%94%9F%E9%B2%9C%E6%B0%B4%E6%9E%9C%203%E6%96%A4JJ%E7%BA%A7.jpg?sign=f3oCa5DIbtU62WEXzg_HZVxXGJ-WjW1hAPFz5Ml1VPI%3D%3A0',
        '【现货拍下即发】【京东/顺丰快递】【拍多份合并一箱发货介意慎拍哦】送礼水果礼盒热卖中', '这是个备注');
INSERT INTO `goods`
VALUES (14, '美果汇 山竹 水果送礼 生鲜 2.5kg 新鲜水果 ', 139.99, 6, 100, 68, 0,
        'https://pan.imzdx.top/api/v3/file/get/42/%E7%BE%8E%E6%9E%9C%E6%B1%87%20%E5%B1%B1%E7%AB%B9%20%E6%B0%B4%E6%9E%9C%E9%80%81%E7%A4%BC%20%E7%94%9F%E9%B2%9C%202.5kg%20%E6%96%B0%E9%B2%9C%E6%B0%B4%E6%9E%9C.jpg?sign=f_UmzfDq9IHNW2kSg8hiekkxq8_-KllXSdDovMmNkek%3D%3A0',
        '【温馨提示：山竹收到请及时放入冰箱冷藏，常温保存出现坏果，不做售后处理，】', '这是个备注');
INSERT INTO `goods`
VALUES (38, 'test', 2.00, 1, 99999, 29997, 0, '1', '1', '1');

-- ----------------------------
-- Table structure for goods_type
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type`
(
    `tyid`   int                                                           NOT NULL AUTO_INCREMENT,
    `tyname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型名',
    PRIMARY KEY (`tyid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods_type
-- ----------------------------
INSERT INTO `goods_type`
VALUES (1, '化妆品');
INSERT INTO `goods_type`
VALUES (2, '零食');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `oid`            int                                                       NOT NULL AUTO_INCREMENT,
    `uid`            int                                                       NOT NULL COMMENT '用户的编号',
    `gid`            int                                                       NOT NULL COMMENT '商品的编号',
    `ordertime`      datetime(0)                                               NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '下单时间',
    `state`          int                                                       NOT NULL DEFAULT 0 COMMENT '订单状态，0未付款1已付款2已取消',
    `price`          decimal(10, 2)                                            NOT NULL COMMENT '应付款',
    `discount`       decimal(10, 2)                                            NULL     DEFAULT 1.00 COMMENT '折扣',
    `pay`            decimal(10, 2)                                            NULL     DEFAULT NULL,
    `goods_snapshot` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '实付款',
    `user_snapshot`  longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用户快照',
    `sid`            int                                                       NULL     DEFAULT NULL COMMENT '如果是秒杀订单则填写秒杀ID',
    PRIMARY KEY (`oid`) USING BTREE,
    INDEX `ck_2` (`gid`) USING BTREE,
    INDEX `ck_3` (`uid`) USING BTREE,
    INDEX `fk_4` (`sid`) USING BTREE,
    CONSTRAINT `ck_2` FOREIGN KEY (`gid`) REFERENCES `goods` (`gid`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `ck_3` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_4` FOREIGN KEY (`sid`) REFERENCES `seckill` (`sid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2419647
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for seckill
-- ----------------------------
DROP TABLE IF EXISTS `seckill`;
CREATE TABLE `seckill`
(
    `sid`       int     NOT NULL AUTO_INCREMENT,
    `gid`       int     NOT NULL,
    `startday`  date    NOT NULL,
    `starttime` time(0) NOT NULL,
    `endday`    date    NOT NULL,
    `endtime`   time(0) NOT NULL,
    `data`      json    NULL,
    `usecount`  int     NULL DEFAULT 0,
    PRIMARY KEY (`sid`) USING BTREE,
    INDEX `fk_gid` (`gid`) USING BTREE,
    CONSTRAINT `fk_gid` FOREIGN KEY (`gid`) REFERENCES `goods` (`gid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  AUTO_INCREMENT = 13
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of seckill
-- ----------------------------
INSERT INTO `seckill`
VALUES (1, 12, '2021-04-15', '00:00:00', '2021-04-15', '00:00:00', '[
  {
    \"end\": 1000,
    \"top\": 0,
    \"discount\": 0.8
  },
  {
    \"end\": 3000,
    \"top\": 1001,
    \"discount\": 0.9
  }
]', 0);
INSERT INTO `seckill`
VALUES (4, 8, '2021-09-06', '08:00:00', '2021-09-07', '09:00:00', '[
  {
    \"end\": 1000,
    \"top\": 0,
    \"discount\": 0.8
  },
  {
    \"end\": 2000,
    \"top\": 1001,
    \"discount\": 0.9
  }
]', 0);
INSERT INTO `seckill`
VALUES (8, 1, '2021-05-19', '19:11:00', '2021-05-19', '19:20:00', '[
  {
    \"end\": 1000,
    \"top\": 0,
    \"discount\": 0.8
  },
  {
    \"end\": 2000,
    \"top\": 1001,
    \"discount\": 0.9
  },
  {
    \"end\": 2002,
    \"top\": 2001,
    \"discount\": 1
  }
]', 2);
INSERT INTO `seckill`
VALUES (10, 2, '2021-04-22', '12:00:00', '2021-09-07', '09:00:00', '[
  {
    \"end\": 10,
    \"top\": 1,
    \"discount\": 0.8
  },
  {
    \"end\": 20,
    \"top\": 11,
    \"discount\": 0.9
  }
]', 591220);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `uid`      int                                                           NOT NULL AUTO_INCREMENT,
    `uname`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `phone`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL,
    `email`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `birthday` date                                                          NULL     DEFAULT NULL,
    `regtime`  datetime(0)                                                   NULL     DEFAULT NULL COMMENT '注册时间 ',
    `logo`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `type`     int                                                           NOT NULL DEFAULT 0 COMMENT '0为普通1为管理员',
    PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 18
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user`
VALUES (1, '刘思铭', '333333', '13036798478', '1760602325@qq.com', '2002-06-02', '2021-04-06 21:09:00',
        'https://pan.imzdx.top/api/v3/file/get/46/lsm-logo.jpg?sign=S0Y1XgI5Ce-QdoW1kyHKH_DB4m9qvmdBFlPpQQFYKn0%3D%3A0',
        1);
INSERT INTO `user`
VALUES (2, '张东祥', '123456', '18156551486', '1277489864@qq.com', '2001-12-26', '2021-04-07 00:00:00',
        'https://pan.imzdx.top/api/v3/file/get/1/logo.jpg?sign=aqIOCQ8uRJbaLfzuo0Rlr_P9334cqAI2Chm-DtEDs5A%3D%3A0', 1);
INSERT INTO `user`
VALUES (3, 'admin', '123123', '18888888888', '1277489864@qq.com', '2001-12-26', '2021-04-07 19:56:51',
        'https://pan.imzdx.top/api/v3/file/get/1/logo.jpg?sign=aqIOCQ8uRJbaLfzuo0Rlr_P9334cqAI2Chm-DtEDs5A%3D%3A0', 1);
INSERT INTO `user`
VALUES (4, 'test', '123123', '16666666666', '1277489864@qq.com', '2020-01-12', '2021-04-07 20:34:39',
        'https://pan.imzdx.top/api/v3/file/get/1/logo.jpg?sign=aqIOCQ8uRJbaLfzuo0Rlr_P9334cqAI2Chm-DtEDs5A%3D%3A0', 0);
INSERT INTO `user`
VALUES (14, '嘿嘿123', '123456', '112', '113@ww.cq', '2002-07-14', '2021-04-17 15:17:48', NULL, 0);
INSERT INTO `user`
VALUES (17, '小思', '111111', '1', '1760602325@qq.com', '2021-04-23', '2021-04-23 07:34:00', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
