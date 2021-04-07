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
        }
        if (userDao.insertByUser(uname,password,phone,email, birthday)==1){
            return 200;
        }else return 202;//异常
    }


}
