package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.*;
import top.imzdx.storequeue.pojo.Order;

import java.util.List;

@Mapper
public interface OrderDao {
    @Insert({
            "<script> " +
                    "INSERT INTO `order`(`uid`, `gid`, `ordertime`,`state`, `price`,`discount`,`pay`, `goods_snapshot`, `user_snapshot`,`sid`) " +
                    "VALUES (#{order.uid}, #{order.gid}, now(),#{order.state}, #{order.price}, #{order.discount}, #{order.pay}, #{order.goods_snapshot}, #{order.user_snapshot}, " +
                    "<if test='order.sid==0'>null</if>" +
                    "<if test='order.sid!=0'>#{order.sid}</if>" +
                    ")" +
                    "</script>"
    })
    int insertOrder(@Param("order") Order order);

    @Select("SELECT * FROM `order`")
    List<Order> getAllOrder();

    @Select("SELECT * FROM `order` WHERE uid = #{uid}")
    List<Order> getOrderByUid(long uid);

    @Select("SELECT * FROM `order` WHERE oid = #{oid}")
    Order getOrderByOid(long oid);

    @Update("update `order` set oid=#{order.oid},uid=#{order.uid},gid=#{order.gid},ordertime=#{order.ordertime},state=#{order.state},price=#{order.price},goods_snapshot=#{order.goods_snapshot},user_snapshot=#{order.user_snapshot} where oid=#{order.oid}")
    int update(@Param("order") Order order);
}
