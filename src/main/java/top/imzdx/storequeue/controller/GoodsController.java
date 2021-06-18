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

    /**
     * 获得所有商品分类
     * @return 所有商品分类
     */
    @GetMapping("category")
    public Result getCategory() {
        return new ResultTools().success("获取成功", goodsService.getCategory());
    }

    /**
     * 根据分类获取所有未下架的商品
     * @param category 分类id
     * @return 所有未下架的商品
     */
    @GetMapping("")
    public Result getGoods(String category) {
        List<Goods> i = goodsService.getGoods(category);
        return new ResultTools().success("获取成功", i);
    }

    /**
     *根据分类获取所有商品（包括下架的）
     * @param category 分类id
     * @return 所有商品（包括下架的）
     */
    @GetMapping("all")
    @AdminRequired
    public Result getAllGoods(String category) {
        List<Goods> i = goodsService.getAllGoods(category);
        return new ResultTools().success("获取成功", i);
    }

    /**
     * 随机获取n个未下架的商品
     * @param n 需要获取的数量
     * @return 未下架的n个商品
     */
    @GetMapping("random")
    public Result getGoodsRandom(int n) {
        List<Goods> i = goodsService.getGoodsRandom(n);
        return new ResultTools().success("获取成功", i);
    }

    /**
     *根据商品id获取商品
     * @param id 商品id
     * @return 200:指定商品 201:商品不存在
     */
    @GetMapping("{id}")
    public Result getGoods(@PathVariable long id) {
        Goods i = goodsService.getGoods(id);
        if (i != null) {
            return new ResultTools().success("获取成功", i);
        } else {
            return new ResultTools().fail(201, "商品不存在", null);
        }
    }
    /**
     * 获得指定商品的秒杀活动
     * @param gid 商品id
     * @return
     */
    @GetMapping("{gid}/seckill")
    public Result getSeckillByGid(@PathVariable String gid) {
        Seckill i = goodsService.getSeckillByGid(Integer.parseInt(gid));
        if (i != null) {
            return new ResultTools().success("获取成功", i);
        } else {
            return new ResultTools().fail(201, "获取失败", i);
        }
    }

    /**
     *
     * @param gname
     * @param price
     * @param category
     * @param total
     * @param stock
     * @param state
     * @param pic
     * @param details
     * @param remarks
     * @return
     */
    @PostMapping("new")
    @AdminRequired
    public Result newGoods(String gname, double price, int category, int total, int stock, int state, String pic, String details, String remarks) {
        if (goodsService.newGoods(gname, price, category, total, stock, state, pic, details, remarks) == 1) {
            return new ResultTools().success("添加成功", null);
        } else {
            return new ResultTools().fail(201, "添加失败", null);
        }
    }

    /**
     * 删除商品
     * @param gid 商品id
     * @return result结果集
     */
    @GetMapping("delete")
    @AdminRequired
    public Result deleteGoods(String gid) {
        if (goodsService.deleteGoods(Long.parseLong(gid)) == 1) {
            return new ResultTools().success("删除成功", null);
        } else {
            return new ResultTools().fail(201, "删除失败", null);
        }
    }

    static boolean notNull(String gid, String gname, String price, String category, String total, String stock, String state, String pic) {
        if (gid == null || gname == null || price == null || category == null || total == null || stock == null || state == null || pic == null) {
            return true;
        }
        return false;
    }

    /**
     * 修改商品
     * @param gid
     * @param gname
     * @param price
     * @param category
     * @param total
     * @param stock
     * @param state
     * @param pic
     * @param details
     * @param remarks
     * @return
     */
    @PostMapping("edit")
    @AdminRequired
    public Result editGoods(String gid, String gname, String price, String category, String total, String stock, String state, String pic, String details, String remarks) {
        if (notNull(gid, gname, price, category, total, stock, state, pic)) {
            return new ResultTools().fail(201, "参数不完整", null);
        }
        try {
            if (goodsService.editGoods(Integer.parseInt(gid), gname, Double.parseDouble(price), Integer.parseInt(category), Integer.parseInt(total), Integer.parseInt(stock), Integer.parseInt(state), pic, details, remarks) == 1) {
                return new ResultTools().success("修改成功", null);
            } else {
                return new ResultTools().fail(202, "与原信息一样 无需修改", null);
            }
        } catch (NumberFormatException e) {
            return new ResultTools().fail(203, "参数格式错误", null);
        }
    }

    /**
     * 一个购买方法
     * @param gid 物品id
     * @param session 登录状态
     * @return
     */
    @LoginRequired
    @GetMapping("{gid}/buy")
    public Result buy(@PathVariable long gid, HttpSession session) {
        try {
            long time = goodsService.buyCreate(gid, ((User) session.getAttribute("user")).getUid());
            if (time == -1) {
                return new ResultTools().fail(202, "商品不存在", null);
            }
            return new ResultTools().success("您的购买请求已提交，正在排队中，请稍后在'我的-订单列表'内查询您的订单。", time);
        } catch (NumberFormatException e) {
            return new ResultTools().fail(201, "参数错误", null);
        }
    }
}