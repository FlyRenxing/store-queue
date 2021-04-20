package top.imzdx.storequeue.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.interceptor.AdminRequired;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;
import top.imzdx.storequeue.service.SeckillService;

@RestController
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillService seckillService;

    @AdminRequired
    @PostMapping("new")
    public Result newSecikill(int gid,String startdate,String starttime,String enddate,String endtime,String data) {
        if (seckillService.newSeckill(gid,startdate, starttime,enddate, endtime, data)==1){
            return new ResultTools().success("新增成功",null);
        }else {
            return  new ResultTools().fail(201,"新增失败",null);
        }
    }

    @AdminRequired
    @PostMapping("delete")
    public Result deleteSeckill(int sid){
        if (seckillService.deleteSeckill(sid)==1){
            return new ResultTools().success("删除成功",null);
        }else {
            return new ResultTools().fail(201,"gid错误",sid);
        }
    }

    @AdminRequired
    @PostMapping("edit")
    public Result editSeckill(String sid,String gid,String startdate,String starttime,String enddate,String endtime,String data){
        if (sid==null ||gid==null||startdate==null||starttime==null ||enddate==null||endtime==null||data==null){
            return  new ResultTools().fail(201,"参数不完整",null);
        }
        try {
            if (seckillService.editSeckill(Integer.parseInt(sid), Integer.parseInt(gid), startdate, starttime, enddate, endtime, data) == 1) {
                return new ResultTools().success("修改成功", null);
            } else {
                return new ResultTools().fail(202, "修改失败", null);
            }
        } catch (NumberFormatException e) {
           return  new ResultTools().fail(203,"异常",null);
        }

    }

    @GetMapping("")
    public Result getAllSecikill() {
        return new ResultTools().success("获取成功",seckillService.getAllSeckill());
    }
}
