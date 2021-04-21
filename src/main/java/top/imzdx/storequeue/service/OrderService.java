package top.imzdx.storequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.OrderDao;
import top.imzdx.storequeue.pojo.goods.Order;

@Service
public class OrderService {
    @Autowired
    private  OrderDao orderDao;

    public int newOrder(Order order) {
        return orderDao.insetOrder(order);
    }
}
