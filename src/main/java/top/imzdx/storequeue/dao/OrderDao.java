package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.*;
import top.imzdx.storequeue.pojo.goods.Order;

@Mapper
public interface OrderDao {
    @Insert("INSERT INTO `order`(`uid`, `gid`, `ordertime`, `price`, `goods_snapshot`, `user_snapshot`) VALUES (#{order.uid}, #{order.gid}, now(), #{order.price}, #{order.goods_snapshot}, #{order.user_snapshot})")
    int insetOrder(@Param("order")Order order);
}
