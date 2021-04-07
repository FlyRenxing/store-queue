package top.imzdx.storequeue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            User userinfo = user;
            userinfo.setPassword("***");
            return new ResultTools().success("登陆成功", userinfo);
        } else {
            return new ResultTools().fail(201, "账号或密码错误", null);
        }

    }

    @PostMapping("register")
    public Result register(String uname,String password,String phone,String email,String birthday) {
        int code=userService.register(uname,password,phone,email,birthday);
        if (code==200){
            return new ResultTools().success("注册成功", null);
        }else return new ResultTools().fail(code,"注册失败",null);
    }

    @GetMapping("profile")
    public Result look(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return new ResultTools().success("获取成功", user);
        } else {
            return new ResultTools().fail(201, "当前未登录", null);
        }
    }

    @GetMapping("logout")
    public Result logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new ResultTools().success("退出成功", null);
    }
}
