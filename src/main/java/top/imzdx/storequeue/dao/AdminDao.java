package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Renxing
 * @description
 * @date 2021/4/17 20:01
 */
@Mapper
public interface AdminDao {
    @Select("SELECT IFNULL(SUM(price),0) FROM `order`;")
    double getGvm();

    @Select("SELECT COUNT(1) FROM goods")
    int getGoodNum();

    @Select("SELECT COUNT(1) FROM `order`")
    int getOrderNum();

    @Select("SELECT COUNT(1) FROM user")
    int getUserNum();
}
