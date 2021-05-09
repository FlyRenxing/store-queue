package top.imzdx.storequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.UserDao;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.redis.RedisUtil;

import java.util.List;

/**
 * @author Renxing
 * @description
 * @date 2021/4/6 21:36
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisUtil redisUtil;

    public User login(String uname, String password) {
        return userDao.getUserByNameAndPassword(uname, password);
    }

    public int equalsUName(String uname) {
        return userDao.getEqulsUName(uname);
    }

    public int register(String uname, String password, String phone, String email, String birthday) {
        User user = new User();
        user.setUname(uname);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBirthday(birthday);

        return userDao.insertToUser(user);
    }

    public int register(String uname, String password, String phone, String email, String birthday, int type, String logo) {
        User user = new User();
        user.setUname(uname);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setType(type);
        user.setLogo(logo);

        return userDao.insertToUser(user);
    }

    public int changePassword(String uname, String newPassword, String phone, String email) {//忘记密码
        User user = userDao.getUsreByName(uname);
        if (user == null) {
            //查无此人，用户名错误
            return 201;
        } else {
            if (user.getPhone().equals(phone) && user.getEmail().equals(email)) {
                if (userDao.changePasswordByUname(newPassword, uname) == 1) {
                    //正确，可以修改
                    return 200;
                } else {
                    //密码重复
                    return 202;
                }
            } else {
                //电话、邮箱不正确
                return 203;
            }
        }
    }

    public int modifyUserInfo(String phone, String email, String birthday, long uid) {
        int n = userDao.changeUserInfoByUid(phone, email, birthday, uid);
        if (n == 1) {
            //修改成功
            return 200;
        } else if (n == 0) {
            //信息与原信息相同
            return 201;
        }
        //修改了很多信息...
        return 202;
    }

    public int changePassword(long uid, String oldPassword, String newPassword) {
        if (!userDao.getUsreByUid(uid).getPassword().equals(oldPassword)) {
            return 201;
        }
        int n = userDao.changePasswordByUid(uid, newPassword);
        if (n == 1) {
            return 200;
        } else {
            return 203;
        }
    }

    public User findUserByUid(long uid) {
        User user = (User) (redisUtil.hget("user", Long.toString(uid)));
        if (user == null) {
            user = userDao.getUsreByUid(uid);
            redisUtil.hset("user", Long.toString(uid), user);
        }
        return user;
    }

    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    public int updateUser(long uid, String uname, String password, String phone, String email, String birthday, int type, String logo) {
        User user = new User();
        user.setType(type);
        user.setUid(uid);
        user.setUname(uname);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setLogo(logo);
        redisUtil.hset("user", Long.toString(uid), user);
        return userDao.updateUser(user);
    }

    public int deleteUser(long uid) {
        redisUtil.hdel("user", uid);
        return userDao.deleteUser(uid);
    }
}
