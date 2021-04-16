package top.imzdx.storequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.UserDao;
import top.imzdx.storequeue.pojo.User;

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

    public User login(String uname, String password) {
        return userDao.getUserByNameAndPassword(uname, password);
    }


    public int EqualsUName(String uname) {
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

    public int changePassword(String uname, String newPassword, String phone, String email) {//忘记密码
        User user = userDao.getUsreByName(uname);
        if (user == null) {
            return 201;//查无此人，用户名错误
        } else {
            if (user.getPhone().equals(phone) && user.getEmail().equals(email)) {
                if (userDao.changePasswordByUname(newPassword, uname) == 1) {
                    return 200;//正确，可以修改
                } else return 202;//密码重复
            } else {
                //System.out.println(user.toString());
                return 203;//电话、邮箱不正确
            }
        }
    }

    public int modifyUserInfo(String phone, String email, String birthday, long uid) {
        int n = userDao.changeUserInfoByUid(phone, email, birthday, uid);
        if (n == 1) {
            return 200;//修改成功
        } else if (n == 0) {
            return 201;//信息与原信息相同
        }
        return 202;//修改了很多信息...
    }


    public int changePassword(long uid, String oldPassword, String newPassword) {
        if (!userDao.getUsreByUid(uid).getPassword().equals(oldPassword)) {
            return 201;
        }
        int n = userDao.changePasswordByUid(uid, newPassword);
        if (n == 1) {
            return 200;
        } else return 203;
    }

    public User findUserByUid(long uid) {
        return userDao.getUsreByUid(uid);
    }

    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    public int UpdateUser(long uid, String uname, String password, String phone, String email, String birthday, int type, String logo) {
        User user = new User();
        user.setType(type);
        user.setUid(uid);
        user.setUname(uname);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setLogo(logo);
        //System.out.println(user);
        return userDao.updateUser(user);
    }
}
