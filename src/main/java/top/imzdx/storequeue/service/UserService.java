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
}
