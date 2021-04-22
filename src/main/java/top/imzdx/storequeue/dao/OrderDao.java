package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.imzdx.storequeue.pojo.Order;

import java.util.List;

@Mapper
public interface OrderDao {
    @Insert("INSERT INTO `order`(`uid`, `gid`, `ordertime`, `price`, `goods_snapshot`, `user_snapshot`) VALUES (#{order.uid}, #{order.gid}, now(), #{order.price}, #{order.goods_snapshot}, #{order.user_snapshot})")
    int insetOrder(@Param("order") Order order);

    @Select("SELECT * FROM `order`")
    List<Order> getAllOrder();
}
