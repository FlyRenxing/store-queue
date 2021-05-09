package top.imzdx.storequeue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @LoginRequired
    @GetMapping("{oid}/pay")
    public Result pay(@PathVariable long oid, HttpSession session) {
        long uid = ((User) session.getAttribute("user")).getUid();
        int code = orderService.pay(uid, oid);
        if (code == 200) {
            return new ResultTools().success("支付成功", null);
        } else if (code == 201) {
            return new ResultTools().fail(code, "您的帐号下无该订单", null);
        } else {
            return new ResultTools().fail(202, "未知错误", null);
        }
    }

    @LoginRequired
    @GetMapping("{oid}/close")
    public Result closeOrder(@PathVariable long oid, HttpSession session) {
        long uid = ((User) session.getAttribute("user")).getUid();
        int code = orderService.close(uid, oid);
        if (code == 200) {
            return new ResultTools().success("关闭成功", null);
        } else if (code == 201) {
            return new ResultTools().fail(code, "您的帐号下无该订单", null);
        } else {
            return new ResultTools().fail(202, "未知错误", null);
        }
    }

}
