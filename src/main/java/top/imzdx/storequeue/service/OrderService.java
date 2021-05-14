package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.OrderDao;
import top.imzdx.storequeue.pojo.Order;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Goods;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;

    public int insertOrder(Order order) {
        return orderDao.insertOrder(order);
    }

    public List<Order> getAllOrder() {
        return orderDao.getAllOrder();
    }

    public List<Order> getUserOrder(long uid) {
        return orderDao.getOrderByUid(uid);
    }

    public Order create(Goods goods, User user) {
        Order order = new Order();
        user.setPassword("****");

        order.setUid(user.getUid());
        order.setGid(goods.getGid());
        order.setPrice(goods.getPrice());
        order.setDiscount(1);
        order.setPay(goods.getPrice());

        String goodsSnapshot = JSON.toJSONString(goods);
        order.setGoods_snapshot(goodsSnapshot);

        String userSnapshot = JSON.toJSONString(user);
        order.setUser_snapshot(userSnapshot);
        return order;
    }

    public Order create(Goods goods, User user, Seckill seckill) {
        Order order = new Order();
        user.setPassword("****");

        order.setUid(user.getUid());
        order.setGid(goods.getGid());
        order.setSid(seckill.getSid());
        String goodsSnapshot = JSON.toJSONString(goods);
        order.setGoods_snapshot(goodsSnapshot);

        String userSnapshot = JSON.toJSONString(user);
        order.setUser_snapshot(userSnapshot);

        order.setPrice(goods.getPrice());
        if (seckillService.isSeckillTime(seckill)) {
            if (seckillService.isSeckillRange(seckill)) {
                double discount = seckillService.getRangeDiscount(seckill);
                order.setDiscount(discount);
                order.setPay(order.getDiscount() * goods.getPrice());
            } else {
                order.setDiscount(1);
                order.setPay(goods.getPrice());
            }
        } else {
            order.setDiscount(1);
            order.setPay(goods.getPrice());
        }
        return order;
    }

    public int changeState(long uid, long oid, int stateCode) {
        Order order = orderDao.getOrderByOid(oid);
        if (order != null && order.getUid() == uid) {
            order.setState(stateCode);
            if (orderDao.update(order) == 1) {
                return 200;
            }
        }
        return 201;
    }

    public ArrayList<Order> getOrderBySid(long sid) {

        return orderDao.getOrderBySid(sid);
    }
}
