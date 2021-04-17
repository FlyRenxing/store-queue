package top.imzdx.storequeue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.interceptor.AdminRequired;
import top.imzdx.storequeue.interceptor.LoginRequired;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;
import top.imzdx.storequeue.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Renxing
 * @description
 * @date 2021/4/6 20:10
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(HttpServletRequest request, String uname, String password) {
        User user = userService.login(uname, password);
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
//刘思铭
        if (user != null) {
            user.setPassword("***");
            return new ResultTools().success("登陆成功", user);
        } else {
            return new ResultTools().fail(201, "账号或密码错误", null);
        }

    }

    @PostMapping("register")
    public Result register(String uname, String password, String phone, String email, String birthday) {
        if (userService.EqualsUName(uname) != 0) {
            return new ResultTools().fail(201, "用户名重复", null);//用户名重复
        } else if (userService.register(uname, password, phone, email, birthday) == 1) {
            return new ResultTools().success("注册成功", null);
        } else return new ResultTools().fail(203, "系统异常", null);//异常
    }

    @GetMapping("profile")
    @LoginRequired
    public Result look(HttpServletRequest request) {
        HttpSession session = request.getSession();
        long uid = ((User) session.getAttribute("user")).getUid();
        User user = userService.findUserByUid(uid);
        return new ResultTools().success("获取成功", user);
    }

    @GetMapping("logout")
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new ResultTools().success("退出成功", null);
    }

    @PostMapping("forgetpassword")
    public Result forgetPassword(String uname, String phone, String email, String newpassword) {
        int code = userService.changePassword(uname, newpassword, phone, email);
        if (code == 200) {
            return new ResultTools().success("修改密码成功", null);
        }
        if (code == 201) {
            return new ResultTools().fail(201, "用户名错误", null);
        } else if (code == 202) {
            return new ResultTools().fail(202, "密码重复", null);
        } else if (code == 203) {
            return new ResultTools().fail(203, "信息错误", null);
        } else return new ResultTools().fail(204, "异常", null);
    }

    @PostMapping("editpassword")
    @LoginRequired
    public Result editPassword(HttpServletRequest request, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            return new ResultTools().fail(202, "密码与原密码相同", null);
        }
        long uid = ((User) request.getSession().getAttribute("user")).getUid();
        int code = userService.changePassword(uid, oldPassword, newPassword);
        if (code == 200) {
            return new ResultTools().success("修改密码成功", null);
        } else if (code == 201) {
            return new ResultTools().fail(201, "原密码错误", null);
        } else return new ResultTools().fail(code, "系统异常", null);
    }

    @GetMapping("test")
    public Result test() {
        return new ResultTools().success("测试成功", null);
    }

    //修改用户信息
    @LoginRequired
    @PostMapping("modifyuserinfo")
    public Result modifyUserInfo(HttpServletRequest request, String phone, String email, String birthday) {
        HttpSession session = request.getSession();//获取当前会话
        long uid = ((User) session.getAttribute("user")).getUid();
        int code = userService.modifyUserInfo(phone, email, birthday, uid);
        if (code == 200) {
            session.setAttribute("user", userService.findUserByUid(uid));
            return new ResultTools().success("修改信息成功", null);

        } else if (code == 201) {
            return new ResultTools().fail(code, "信息与原信息相同", null);
        } else {
            return new ResultTools().fail(code, "系统异常", null);
        }
    }

    @GetMapping("all")
    @AdminRequired
    public Result getAllUser() {
        return new ResultTools().success("获取成功", userService.getAllUser());
    }

    @PostMapping("edit")
    @AdminRequired
    public Result editUser(String uid, String uname, String password, String phone, String email, String birthday, String type, String logo) {
        if (!(uid != null && uname != null && password != null && phone != null && email != null && birthday != null && type != null && logo != null)) {
            return new ResultTools().fail(201, "参数不完整", null);
        }
        try {
            if (userService.UpdateUser(Integer.parseInt(uid), uname, password, phone, email, birthday, Integer.parseInt(type), logo) == 1) {
                return new ResultTools().success("修改成功", null);
            } else {
                return new ResultTools().fail(203, "查无此人", null);
            }
        } catch (NumberFormatException e) {
            return new ResultTools().fail(202, "参数格式不正确", null);
        }

    }

    @PostMapping("new")
    @AdminRequired
    public Result newUser(String uname, String password, String phone, String email, String birthday, String type, String logo) {
        if (!(uname != null && password != null && phone != null && email != null && birthday != null && type != null && logo != null)) {
            return new ResultTools().fail(201, "参数不完整", null);
        }

        if (userService.EqualsUName(uname) != 0) {
            return new ResultTools().fail(204, "用户名已存在", null);
        }
        try {
            if (userService.newUser(uname, password, phone, email, birthday, Integer.parseInt(type), logo) == 1) {
                return new ResultTools().success("新建成功", null);
            } else {
                return new ResultTools().fail(203, "新建失败", null);
            }
        } catch (NumberFormatException e) {
            return new ResultTools().fail(202, "参数格式不正确", null);
        }
    }

    @GetMapping("delete")
    @AdminRequired
    public Result deleteUser(String uid) {
        try {
            if (userService.deleteUser(Long.parseLong(uid)) == 1) {
                return new ResultTools().success("删除成功", null);
            } else {
                return new ResultTools().fail(201, "删除失败", null);
            }
        } catch (Exception e) {
            return new ResultTools().fail(202, "参数格式有误", null);
        }
    }
}
