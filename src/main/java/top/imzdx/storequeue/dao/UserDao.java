package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.*;
import top.imzdx.storequeue.pojo.User;

import java.util.List;

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

    @Insert("INSERT INTO user(`uname`, `password`, `phone`, `email`, `birthday`, `regtime`)VALUES (#{user.uname}, #{user.password}, #{user.phone}, #{user.email}, #{user.birthday}, NOW())")
    int insertToUser(@Param("user") User user);

    @Select("select * from user where uname=#{uname}")
    User getUsreByName(@Param("uname") String uname);

    @Select("select * from user where uid=#{uid}")
    User getUsreByUid(@Param("uid") long uid);

    @Update("update user set password=#{password} where uname=#{uname}")
    int changePasswordByUname(@Param("password") String password, @Param("uname") String uanme);

    @Update("update user set phone=#{phone},email=#{email},birthday=#{birthday} where uid=#{uid}")
    int changeUserInfoByUid(@Param("phone") String phone, @Param("email") String email, @Param("birthday") String birthday, @Param("uid") long uid);

    @Update("update user set password=#{newPassword} where uid=#{uid}")
    int changePasswordByUid(@Param("uid") long uid, @Param("newPassword") String newPassword);

    @Select("select * from user")
    List<User> getAllUser();

    @Update("update user set uname=#{user.uname},password=#{user.password},phone=#{user.phone},email=#{user.email},birthday=#{user.birthday},type=#{user.type},logo=#{user.logo} where uid=#{user.uid}")
    int updateUser(@Param("user") User user);
}
