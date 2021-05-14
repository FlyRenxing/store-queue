package top.imzdx.storequeue.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public Result newSecikill(int gid, String startday, String starttime, String endday, String endtime, String data) {
        int code = seckillService.newSeckill(gid, startday, starttime, endday, endtime, data);
        if (code == 1) {
            return new ResultTools().success("新增成功", null);
        } else if (code == -1) {
            return new ResultTools().fail(201, "该商品对应的营销活动已存在", null);
        } else {
            return new ResultTools().fail(202, "新增失败", null);
        }
    }

    @AdminRequired
    @GetMapping("{sid}/delete")
    public Result deleteSeckill(@PathVariable long sid) {
        if (seckillService.deleteSeckill(sid) == 1) {
            return new ResultTools().success("删除成功", null);
        } else {
            return new ResultTools().fail(201, "gid错误", sid);
        }
    }

    @GetMapping("{sid}/seckillOrderList")
    public Result getSeckillOrderList(@PathVariable long sid) {

        return new ResultTools().success("成功", seckillService.getSeckillOrderList(sid));
    }

    @AdminRequired
    @PostMapping("edit")
    public Result editSeckill(String sid, String gid, String startday, String starttime, String endday, String endtime, String data, String usecount) {
        if (GoodsController.notNull(sid, gid, startday, starttime, endday, endtime, data, usecount)) {
            return new ResultTools().fail(201, "参数不完整", null);
        }
        try {
            if (seckillService.editSeckill(Integer.parseInt(sid), Integer.parseInt(gid), startday, starttime, endday, endtime, data, usecount) == 1) {
                return new ResultTools().success("修改成功", null);
            } else {
                return new ResultTools().fail(202, "修改失败", null);
            }
        } catch (NumberFormatException e) {
            return new ResultTools().fail(203, "异常", null);
        }

    }

    @GetMapping("")
    public Result getAllSecikill() {
        return new ResultTools().success("获取成功",seckillService.getAllSeckill());
    }
}
