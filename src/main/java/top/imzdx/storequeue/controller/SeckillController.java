package top.imzdx.storequeue.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.service.SeckillService;

@RestController
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @PostMapping("new")
    public void newSecikill(int gid, String starttime, String endtime, String data) {

    }

    @GetMapping("all")
    public void getAllSecikill() {

    }
}
