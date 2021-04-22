package top.imzdx.storequeue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.interceptor.AdminRequired;
import top.imzdx.storequeue.interceptor.LoginRequired;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;
import top.imzdx.storequeue.service.OrderService;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @LoginRequired
    @GetMapping("")
    public Result getUserOrder(HttpSession session) {
        long uid = ((User) session.getAttribute("user")).getUid();
        return new ResultTools().success("获取成功", orderService.getUserOrder(uid));
    }

    @AdminRequired
    @GetMapping("all")
    public Result getAllOrder() {
        return new ResultTools().success("获取成功", orderService.getAllOrder());
    }

}
