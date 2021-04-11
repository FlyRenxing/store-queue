package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.*;
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
    int insertToUser(@Param("uname")String uname,@Param("password")String password,@Param("phone")String phone,@Param("email")String email,@Param("birthday")String birthday);

    @Select("select * from user where uname=#{uname}")
    User getUsreByName(@Param("uname")String uname);

    @Update("update user set password=#{password} where uname=#{uname}")
    int changePasswordByUname(@Param("password")String password,@Param("uname")String uanme);

    @Update("update user set phone=#{phone},email=#{email},birthday=#{birthday} where uid=#{uid}")
    int changeUserInfoByUname(@Param("phone")String phone,@Param("email")String email,@Param("birthday")String birthday,@Param("uid")long uid);

}
