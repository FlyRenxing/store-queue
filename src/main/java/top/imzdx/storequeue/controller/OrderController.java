package top.imzdx.storequeue.controller;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.interceptor.AdminRequired;
import top.imzdx.storequeue.interceptor.LoginRequired;

@RestController
@RequestMapping("/order")
public class OrderController {
    //对订单的增删改查
    @PostMapping("add")
    @LoginRequired
    public void addOrder(String adid,String gid){//

    }

}
