package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.dao.SeckillDao;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Category;
import top.imzdx.storequeue.pojo.goods.Goods;
import top.imzdx.storequeue.pojo.goods.Order;
import top.imzdx.storequeue.tools.GoodsHandle;

import java.util.List;

/**
 * @author Renxing
 * @description
 * @date 2021/4/10 20:41
 */
@Service
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private GoodsHandle goodsHandle;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    public List<Category> getCategory() {
        return goodsDao.getCategory();
    }

    public List<Goods> getGoods(String category) {
        return goodsHandle.removeGoodsByState(goodsDao.getGoodsByCategory(category));
    }

    public List<Goods> getAllGoods(String category) {
        return goodsDao.getGoodsByCategory(category);
    }

    public Goods getGoods(int id) {
        Goods good = goodsDao.getGoodsById(id);
        if (good.getState() == 1) {
            return null;
        }
        return good;
    }

    public List<Seckill> getSeckillByGid(int gid){
        return seckillDao.selectSeckillByGid(gid);
    }

    public List<Goods> getGoodsRandom(int n) {
        return goodsHandle.removeGoodsByState(goodsDao.getGoodsRandom(n));
    }

    public int newGoods(String gname, double price, int category, int total, int stock, int state, String pic, String details, String remarks) {
        Goods goods = new Goods();
        goods.setGname(gname);
        goods.setPrice(price);
        goods.setCategory(category);
        goods.setTotal(total);
        goods.setStock(stock);
        goods.setState(state);
        goods.setPic(pic);
        goods.setDetails(details);
        goods.setRemarks(remarks);
        return goodsDao.insertGoods(goods);
    }

    public int deleteGoods(long gid) {
        return goodsDao.deleteGoods(gid);
    }

    public int editGoods(int gid, String gname, double price, int category, int total, int stock, int state, String pic, String details, String remarks) {
        Goods goods = new Goods();
        goods.setGid(gid);
        goods.setGname(gname);
        goods.setPrice(price);
        goods.setCategory(category);
        goods.setTotal(total);
        goods.setStock(stock);
        goods.setState(state);
        goods.setPic(pic);
        goods.setDetails(details);
        goods.setRemarks(remarks);
        return goodsDao.updateGoods(goods);
    }

    public Goods selectGoods(long gid){
        return goodsDao.getGoodsById(gid);
    }

    public int  buy(long gid,long uid){//uid需要从controller类获取！
        int stock= selectGoods(gid).getStock();//先获取库存
        Goods goods = goodsDao.getGoodsById(gid);
        if (stock>0) {//如果有库存的话，就减库存
            goods.setStock(goods.getStock() - 1);
            goodsDao.updateGoods(goods);
        }else{
            return -1;//返回-1表示无库存
        }
        //创建订单 调用orderservice 完善order表
        Order order=new Order();
        order.setUid(uid);
        order.setGid(gid);
        order.setPrice(goods.getPrice());//查goods表该商品的价格，保证统一
        String goods_snapshot= JSON.toJSONString(goods);//运用了json
        order.setGoods_snapshot(goods_snapshot);//把他存入order实体类表里

        User user=userService.findUserByUid(uid);
        String user_snapshot=JSON.toJSONString(user);
        order.setUser_snapshot(user_snapshot);//同上

        //直接返回操作order表的结果
        return orderService.newOrder(order);
    }
}
