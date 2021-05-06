package top.imzdx.storequeue.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.dao.OrderDao;
import top.imzdx.storequeue.dao.SeckillDao;

/**
 * @author Renxing
 * @date 2021/5/4 20:44
 */
@Service
public class Listener {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private OrderDao orderDao;
//    @JmsListener(destination = "updateSeckill",containerFactory = "topicListenerContainer")
//    public void updateSeckill(Seckill seckill){
//        seckillDao.updateSeckill(seckill);
//    }
//    @JmsListener(destination = "updateGoods",containerFactory = "topicListenerContainer")
//    public void updateGoods(Goods goods){
//        goodsDao.updateGoods(goods);
//    }
//    @JmsListener(destination = "creatOrder",containerFactory = "topicListenerContainer")
//    public void creatOrder(Order order){
//        orderDao.insertOrder(order);
//    }
}
