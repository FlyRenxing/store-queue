package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.Insert;
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

    @Select("select count(uname) from user where uname=#{uname} ")
    int getEqulsUName(@Param("uname") String uname);

    @Insert("INSERT INTO user(`uname`, `password`, `phone`, `email`, `birthday`, `regtime`)VALUES (#{uname}, #{password}, #{phone}, #{email}, #{birthday}, NOW())")
    int insertByUser(@Param("uname")String uname,@Param("password")String password,@Param("phone")String phone,@Param("email")String email,@Param("birthday")String birthday);
}
