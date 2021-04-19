package top.imzdx.storequeue.controller;


import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill")
public class SeckillController {
    @GetMapping("new")
    public void newSecikill(int gid, String starttime, String endtime, JSON data){
        data[
                int top;
                int end;
                float discount;
                ]
    }
}
