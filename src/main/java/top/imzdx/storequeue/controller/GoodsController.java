package top.imzdx.storequeue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.pojo.goods.Goods;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;
import top.imzdx.storequeue.service.GoodsService;

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

    @GetMapping("category")
    public Result getCategory() {
        return new ResultTools().success("获取成功", goodsService.getCategory());
    }

    @GetMapping("")//根据分类获取商品
    public Result getGoods(String category) {
        //System.out.println(category);
        List<Goods> i = goodsService.getGoods(category);
        return new ResultTools().success("获取成功", i);
    }

    @GetMapping("random")//随机获取商品
    public Result getGoodsRandom(int n) {
        List<Goods> i = goodsService.getGoodsRandom(n);
        return new ResultTools().success("获取成功", i);
    }

    @GetMapping("{id}")//根据id获取商品
    public Result getGoods(@PathVariable long id) {
        //System.out.println(category);
        Goods i = goodsService.getGoods(id);
        return new ResultTools().success("获取成功", i);
    }
}

