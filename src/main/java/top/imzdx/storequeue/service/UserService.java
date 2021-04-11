package top.imzdx.storequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.UserDao;
import top.imzdx.storequeue.pojo.User;

/**
 * @author Renxing
 * @description
 * @date 2021/4/6 21:36
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User login(String uname, String password) {
        return userDao.getUserByNameAndPassword(uname, password);
    }

    public int register(String uname,String password,String phone,String email,String birthday){
        //System.out.println(uname);
        if (userDao.getEqulsUName(uname)!=0) {
            return 201;//用户名重复
        }else if (userDao.insertToUser(uname,password,phone,email, birthday)==1){
            return 200;
        }else return 202;//异常
    }

    public int  changePassword(String uname,String newPassword,String phone,String email){//忘记密码
        User user=userDao.getUsreByName(uname);
        if (user==null){
            return 201;//查无此人，用户名错误
        }else{
            if (user.getPhone().equals(phone) && user.getEmail().equals(email)){
                if (userDao.changePasswordByUname(newPassword,uname)==1){
                    return  200;//正确，可以修改
                }else return 202;//密码重复
            }else{
                System.out.println(user.toString());
                return  203;//电话、邮箱不正确
            }
        }
    }

    public int modifyUserInfo(String phone,String email,String birthday,long uid){
        int n = userDao.changeUserInfoByUname(phone, email, birthday, uid);
        if (n == 1) {
            return 200;//修改成功
        } else if (n == 0) {
            return 201;//信息与原信息相同
        }
        return 202;//修改了很多信息...
    }


}
