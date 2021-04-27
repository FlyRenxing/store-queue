package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.OrderDao;
import top.imzdx.storequeue.pojo.Order;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Goods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public int newOrder(Order order) {
        return orderDao.insertOrder(order);
    }

    public List<Order> getAllOrder() {
        return orderDao.getAllOrder();
    }

    public List<Order> getUserOrder(long uid) {
        return orderDao.getUserOrderByUid(uid);
    }

    public Order create(Goods goods, User user) {
        Order order = new Order();
        user.setPassword("****");

        order.setUid(user.getUid());
        order.setGid(goods.getGid());

        order.setPrice(goods.getPrice());//查goods表该商品的价格，保证统一
        order.setDiscount(1);
        order.setPay(goods.getPrice());

        String goods_snapshot = JSON.toJSONString(goods);//运用了json
        order.setGoods_snapshot(goods_snapshot);//把他存入order实体类表里

        String user_snapshot = JSON.toJSONString(user);
        order.setUser_snapshot(user_snapshot);//同上
        return order;
    }

    public Order create(Goods goods, User user, Seckill seckill) {
        Order order = new Order();
        user.setPassword("****");

        order.setUid(user.getUid());
        order.setGid(goods.getGid());

        String goods_snapshot = JSON.toJSONString(goods);//运用了json
        order.setGoods_snapshot(goods_snapshot);//把他存入order实体类表里

        String user_snapshot = JSON.toJSONString(user);
        order.setUser_snapshot(user_snapshot);//同上

        order.setPrice(goods.getPrice());//查goods表该商品的价格，保证统一
        JSONArray jsonArray = JSONArray.parseArray(seckill.getData());//把data字段里的东西变成一个数组
        if (seckillService.isSeckillTime(seckill)) {
            if (seckillService.isSeckillRange(seckill)) {
                double discount=seckillService.getRangeDiscount(seckill);
                order.setDiscount(discount);//来自jsonObject里 符合人数区间
                order.setPay(order.getDiscount() * goods.getPrice());//来自刚刚存入的order里的discount * goods表里的原价
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

}
