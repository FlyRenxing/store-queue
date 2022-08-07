package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.mq.Producer;
import top.imzdx.storequeue.pojo.Order;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Goods;
import top.imzdx.storequeue.redis.RedisUtil;


/**
 * @author Renxing
 * @date 2021/5/4 20:44
 */
@Service
public class BuyService {
    final public int MEG_HAS_SECKILL = 4;

    final public int ORDER_CREATE_STATE_WAITING = 0;
    final public int ORDER_CREATE_STATE_SUCCESS = 1;
    final public int ORDER_CREATE_STATE_FAIL = 2;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    @Lazy
    private GoodsService goodsService;
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private Producer producer;

    /**
     * 该方法监听购买的创建队列。
     * 打包消息发送至购买的库存队列。
     * <br>
     * 消息体 List=[gid,User,uuid]
     *
     * @param meg JSONArray.toString=[gid,uid,time]
     */
    //@JmsListener(destination = "buy.create", containerFactory = "topicListenerContainer")
    public void buy(String meg) {
        JSONArray array = JSONArray.parseArray(meg);
        long gid = array.getLong(0);
        long uid = array.getLong(1);
        long time = array.getLong(2);
        //在redis内存入uuid以供前端轮询订单状态
        long uuid = Long.parseLong(new StringBuilder().append(uid).append(gid).append(time).toString());
        redisUtil.set(String.valueOf(uuid), ORDER_CREATE_STATE_WAITING);
        array.set(2, uuid);
        User user = userService.findUserByUid(uid);
        array.set(1, user);
        producer.sendMsg("buy.stock", array.toString());
    }

    /**
     * 该方法监听购买的库存队列。
     * 有库存则减库存并打包消息发送给购买的秒杀队列。
     * <br>
     * 无库存则打包消息发送给购买的订单队列。
     * <br>
     * 消息体 JSONArray.toString=[Good,User,uuid]
     *
     * @param meg JSONArray.toString=[gid,uid,uuid]
     */
    //@JmsListener(destination = "buy.stock", containerFactory = "topicListenerContainer")
    public void buyStock(String meg) {
        JSONArray array = JSONArray.parseArray(meg);
        long gid = array.getLong(0);
        Goods goods = goodsService.getGoods(gid);
        array.set(0, goods);
        String arrayMeg = array.toString();
        if (goodsService.hasStock(goods)) {
            goodsService.subStock(goods, 1);
            producer.sendMsg("buy.seckill", arrayMeg);
        } else {
            producer.sendMsg("buy.order", arrayMeg);
        }

    }

    /**
     * 该方法监听购买的秒杀队列。
     * <br>
     * 有秒杀则更新秒杀使用次数记录,并打包消息发送给购买的订单队列。
     * <br>
     * 消息体：JSONArray.toString=[Goods,User,uuid,Seckill]
     * <br>
     * 无秒杀则直接打包消息发送给购买的订单队列。
     * <br>
     * 消息体：JSONArray.toString=[Goods,User,uuid]
     *
     * @param meg JSONArray.toString=[Good,User,uuid]
     */
    //@JmsListener(destination = "buy.seckill", containerFactory = "topicListenerContainer")
    public void buySeckill(String meg) {
        JSONArray array = JSONArray.parseArray(meg);
        Goods goods = array.getObject(0, Goods.class);
        Seckill seckill = seckillService.getSeckillByGid(goods.getGid());
        if (seckill != null) {
            seckill.setUsecount(seckill.getUsecount() + 1);
            seckillService.editSeckill(seckill);
            array.add(seckill);
        }
        producer.sendMsg("buy.order", array.toString());
    }

    /**
     * 该方法监听购买的订单队列。
     * <br>
     * 根据传入参数有无秒杀对象创建订单，若商品库存为0则将订单State设为已取消状态。并将订单写入数据库
     *
     * @param meg 有秒杀时JSONArray.toString=[Goods,User,uuid,Seckill]<br>
     *            无秒杀时JSONArray.toString=[Goods,User,uuid]
     */
    //@JmsListener(destination = "buy.order", containerFactory = "topicListenerContainer")
    public void buyOrder(String meg) {
        JSONArray array = JSONArray.parseArray(meg);
        Goods goods = array.getObject(0, Goods.class);
        User user = array.getObject(1, User.class);
        long uuid = array.getLong(2);
        Order order = null;
        if (array.size() == MEG_HAS_SECKILL) {
            Seckill seckill = array.getObject(3, Seckill.class);
            order = orderService.create(goods, user, seckill);
        } else {
            order = orderService.create(goods, user);
        }
        if (goods.getStock() <= 0) {
            redisUtil.set(String.valueOf(uuid), ORDER_CREATE_STATE_FAIL, 3600);
            order.setState(order.STATE_CLOSE);
        } else {
            redisUtil.set(String.valueOf(uuid), ORDER_CREATE_STATE_SUCCESS, 3600);
        }
        orderService.insertOrder(order);
    }

}
