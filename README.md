## 项目已部署，在线Demo

- 前台商城：[index](http://store.imzdx.top)

## 基于前后端分离的购物电商商城

- 用 户 管 理：包括用户注册和登录（用户登录无需验证码等），完善个人信息（如电话号码等），统计。

- 商品信息管理：商品增删改查，及定时发布抢购，商品信息包括商品名称、价格、图片、详情等。

- 营销活动管理：制定营销策略，某种商品定时发布抢购，根据订购顺序，第1-100名商品1折销售，第101-500名5折，第501-1000名8折，第1001名后商品原价销售。

- 订单管理：订单统计等，按照抢购顺序公示前1000名客户名称、手机号码（星号隐藏第4-7位）及对应折扣。

## 主要内容

![](/doc/res/主页.png)

![](/doc/res/商品分类图.png)

![](/doc/res/订单管理图.png)

## 架构图

![](/doc/商城系统架构图.png)

## 前端所用技术

- html +css +javascript ES6

- Vue+Vue Router+VueX+Axios +Node.js+webpack

## 后端所用技术

- SpringBoot
- Mysql
- JDBC+Mybatis
- Redis+Redisson
- FastJSON
- FlashMQ自研消息队列引擎
- Tomcat
- Nginx
- Maven

## 部署说明及相关文档请见 [doc目录](/doc)

## 前端项目请见 [store-queue-ui](https://github.com/FlyRenxing/store-queue-ui)

## 因版权问题自研消息队列Flash-MQ仅提供编译后版本，如需使用该商城可将消息队列自行替换为其他开源版本。