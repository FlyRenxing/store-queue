package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.imzdx.storequeue.pojo.User;

/**
 * @author Renxing
 * @description
 * @date 2021/4/6 21:32
 */
@Mapper
public interface UserDao {
    @Select("SELECT * FROM `store_queue`.`user` WHERE uname=#{uname} and password=#{password}")
    User getUserByNameAndPassword(@Param("uname") String uname, @Param("password") String password);

}
