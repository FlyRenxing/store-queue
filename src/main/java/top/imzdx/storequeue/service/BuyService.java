package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.mq.Producer;
import top.imzdx.storequeue.pojo.Order;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Goods;


/**
 * @author Renxing
 * @date 2021/5/4 20:44
 */
@Service
public class BuyService {
    final public int MEG_HAS_SECKILL = 3;
    final public int MEG_NO_SECKILL = 2;

    @Autowired
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
     * 消息体 List=[gid,User]
     *
     * @param meg JSONArray.toString=[gid,uid]
     */
    //@JmsListener(destination = "buy.create", containerFactory = "topicListenerContainer")
    public void buy(String meg) {
        System.out.println("buy:" + meg);
        JSONArray array = JSONArray.parseArray(meg);
        long uid = array.getLong(1);
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
     * 消息体 JSONArray.toString=[gid,User]
     *
     * @param meg JSONArray.toString=[gid,uid]
     */
    //@JmsListener(destination = "buy.stock", containerFactory = "topicListenerContainer")
    public void buyStock(String meg) {
        System.out.println("buyStock:" + meg);
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
     * 消息体：JSONArray.toString=[Goods,User,Seckill]
     * <br>
     * 无秒杀则直接打包消息发送给购买的订单队列。
     * <br>
     * 消息体：JSONArray.toString=[Goods,User]
     *
     * @param meg JSONArray.toString=[gid,User]
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
     * @param meg 有秒杀时JSONArray.toString=[Goods,User,Seckill]，无秒杀时JSONArray.toString=[Goods,User]
     */
    //@JmsListener(destination = "buy.order", containerFactory = "topicListenerContainer")
    public void buyOrder(String meg) {
        JSONArray array = JSONArray.parseArray(meg);
        Goods goods = array.getObject(0, Goods.class);
        User user = array.getObject(1, User.class);
        Order order = null;
        if (array.size() == MEG_HAS_SECKILL) {
            Seckill seckill = array.getObject(2, Seckill.class);
            order = orderService.create(goods, user, seckill);
        } else if (array.size() == MEG_NO_SECKILL) {
            order = orderService.create(goods, user);
        }
        System.out.println(goods.toString());
        if (goods.getStock() <= 0) {
            order.setState(order.STATE_CLOSE);
        }
        orderService.insertOrder(order);
    }

}
