package top.imzdx.storequeue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.dao.SeckillDao;
import top.imzdx.storequeue.interceptor.AdminRequired;
import top.imzdx.storequeue.interceptor.LoginRequired;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Goods;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;
import top.imzdx.storequeue.service.GoodsService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Renxing
 * @description
 * @date 2021/4/10 20:38
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private GoodsDao goodsDao;

    @GetMapping("category")
    public Result getCategory() {
        return new ResultTools().success("获取成功", goodsService.getCategory());
    }

    @GetMapping("")//根据分类获取商品
    public Result getGoods(String category) {
        List<Goods> i = goodsService.getGoods(category);
        return new ResultTools().success("获取成功", i);
    }

    @GetMapping("all")//根据分类获取商品
    @AdminRequired
    public Result getAllGoods(String category) {
        List<Goods> i = goodsService.getAllGoods(category);
        return new ResultTools().success("获取成功", i);
    }

    @GetMapping("random")//随机获取商品
    public Result getGoodsRandom(int n) {
        List<Goods> i = goodsService.getGoodsRandom(n);
        return new ResultTools().success("获取成功", i);
    }

    @GetMapping("{id}")//根据id获取商品
    public Result getGoods(@PathVariable int id) {
        Goods i = goodsService.getGoods(id);
        if (i != null) {

            return new ResultTools().success("获取成功", i);
        } else {
            return new ResultTools().fail(201, "商品不存在", null);
        }
    }

    @GetMapping("{gid}/seckill")//根据商品id获得该商品的秒杀活动
    public Result getSeckillByGid(@PathVariable String gid){
        Seckill i = goodsService.getSeckillByGid(Integer.parseInt(gid));
        if (i!=null){
            return new ResultTools().success("获取成功",i);
        }else{
            return new ResultTools().fail(201,"获取失败",i);
        }
    }

    @PostMapping("new")
    @AdminRequired
    public Result newGoods(String gname, double price, int category, int total, int stock, int state, String pic, String details, String remarks) {
        if (goodsService.newGoods(gname, price, category, total, stock, state, pic, details, remarks) == 1) {
            return new ResultTools().success("添加成功", null);
        } else {
            return new ResultTools().fail(201, "添加失败", null);
        }
    }

    @GetMapping("delete")
    @AdminRequired
    public Result deleteGoods(String gid) {
        if (goodsService.deleteGoods(Long.parseLong(gid)) == 1) {
            return new ResultTools().success("删除成功", null);
        } else {
            return new ResultTools().fail(201, "删除失败", null);
        }
    }

    @PostMapping("edit")
    @AdminRequired
    public Result editGoods(String gid, String gname, String price, String category, String total, String stock, String state, String pic, String details, String remarks) {
        if (gid == null || gname == null || price == null || category == null || total == null || stock == null || state == null || pic == null) {
            return new ResultTools().fail(201, "参数不完整", null);
        }
        try {
            if (goodsService.editGoods(Integer.parseInt(gid), gname, Double.parseDouble(price), Integer.parseInt(category), Integer.parseInt(total), Integer.parseInt(stock), Integer.parseInt(state), pic, details, remarks) == 1) {
                return new ResultTools().success("修改成功", null);
            } else {
                return new ResultTools().fail(202, "与原信息一样 无需修改", null);
            }
        } catch (NumberFormatException e) {
            return  new ResultTools().fail(203,"参数格式错误",null);
        }
    }


    @LoginRequired
    @GetMapping("{gid}/buy")
    public Result buy(@PathVariable String gid, HttpSession session){
        try {
            if (goodsService.buy(Long.parseLong(gid),((User)session.getAttribute("user")).getUid())==1){//购买成功
                return new ResultTools().success("购买成功",null);
            }else{
                return new ResultTools().fail(202,"购买失败",null);
            }
        } catch (NumberFormatException e) {
            return new ResultTools().fail(201,"异常",null);
        }
    }
}




