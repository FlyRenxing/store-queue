package top.imzdx.storequeue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.interceptor.AdminRequired;
import top.imzdx.storequeue.interceptor.LoginRequired;
import top.imzdx.storequeue.pojo.Order;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;
import top.imzdx.storequeue.service.OrderService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
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

    @AdminRequired
    @GetMapping("all/page")
    public Result getOrderByPageInfo(int pageNum, int pageSize, String pageSort) {
        List<Order> list = orderService.getOrderByPageInfo(pageNum, pageSize, pageSort);
        return new ResultTools().success("获取成功", list);
    }

    @AdminRequired
    @GetMapping("allCount")
    public Result getOrderCountByAll() {
        return new ResultTools().success("获取成功", orderService.getOrderCountByAll());
    }

    @LoginRequired
    @GetMapping("{oid}/pay")
    public Result pay(@PathVariable long oid, HttpSession session) {
        long uid = ((User) session.getAttribute("user")).getUid();
        int code = orderService.changeState(uid, oid, Order.STATE_ISPAY);
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
        int code = orderService.changeState(uid, oid, new Order().STATE_CLOSE);
        if (code == 200) {
            return new ResultTools().success("关闭成功", null);
        } else if (code == 201) {
            return new ResultTools().fail(code, "您的帐号下无该订单", null);
        } else {
            return new ResultTools().fail(202, "未知错误", null);
        }
    }

    @LoginRequired
    @GetMapping("{uuid}/getState")
    public Result getUuidState(@PathVariable long uuid) {
        return new ResultTools().success("获取成功", orderService.getUuidState(uuid));
    }

}
