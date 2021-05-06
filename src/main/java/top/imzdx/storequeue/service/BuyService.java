package top.imzdx.storequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.mq.Publisher;
import top.imzdx.storequeue.pojo.Order;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Goods;

import java.util.ArrayList;
import java.util.List;


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
    private Publisher publisher;

    /**
     * 该方法监听购买的创建队列。
     * 打包消息发送至购买的库存队列。
     * <br>
     * 消息体List=[gid,User]
     *
     * @param meg long[]={gid,uid}
     */
    @JmsListener(destination = "buy.create", containerFactory = "topicListenerContainer")
    public void buy(long[] meg) {
        long gid = meg[0];
        long uid = meg[1];
        User user = userService.findUserByUid(uid);
        List newMeg = new ArrayList();
        newMeg.add(gid);
        newMeg.add(user);
        publisher.publish("buy.stock", newMeg);
    }

    /**
     * 该方法监听购买的库存队列。
     * 若有库存则减库存并打包消息发送给购买的秒杀队列。
     * <br>
     * 消息体List=[Goods,User]
     *
     * @param meg List=[gid,User]
     */
    @JmsListener(destination = "buy.stock", containerFactory = "topicListenerContainer")
    public void buyStock(List meg) {
        long gid = (long) meg.get(0);
        Goods goods = goodsService.getGoods(gid);
        if (goodsService.hasStock(goods)) {
            goods = goodsService.subStock(goods, 1);
            meg.set(0, goods);
            System.out.println("库存" + goods.getStock());
            publisher.publish("buy.seckill", meg);
        }
        //否则无库存不执行后续操作，即不创建订单

    }

    /**
     * 该方法监听购买的秒杀队列。
     * <br>
     * 有秒杀则更新秒杀使用次数记录,并打包消息发送给购买的订单队列。
     * <br>
     * 消息体：List=[Goods,User，Seckill]
     * <br>
     * 无秒杀则直接打包消息发送给购买的订单队列。
     * <br>
     * 消息体：List=[Goods,User]
     *
     * @param meg List=[Goods,User]
     */
    @JmsListener(destination = "buy.seckill", containerFactory = "topicListenerContainer")
    public void buySeckill(List meg) {
        Goods goods = (Goods) meg.get(0);
        Seckill seckill = seckillService.getSeckillByGid(goods.getGid());
        if (seckill != null) {
            seckill.setUsecount(seckill.getUsecount() + 1);
            seckillService.editSeckill(seckill);
            meg.add(seckill);
        }
        publisher.publish("buy.order", meg);
    }

    /**
     * 该方法监听购买的订单队列。
     * <br>
     * 根据传入参数有无秒杀对象创建订单，并将订单写入数据库
     *
     * @param meg 有秒杀时List=[Goods,User，Seckill]，无秒杀时List=[Goods,User]
     */
    @JmsListener(destination = "buy.order", containerFactory = "topicListenerContainer")
    public void buyOrder(List meg) {
        Goods goods = (Goods) meg.get(0);
        User user = (User) meg.get(1);
        Order order = null;
        if (meg.size() == MEG_HAS_SECKILL) {
            Seckill seckill = (Seckill) meg.get(2);
            order = orderService.create(goods, user, seckill);
        } else if (meg.size() == MEG_NO_SECKILL) {
            order = orderService.create(goods, user);
        }
        orderService.insertOrder(order);
    }

}
