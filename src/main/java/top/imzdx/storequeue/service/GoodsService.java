package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.mq.Producer;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.goods.Category;
import top.imzdx.storequeue.pojo.goods.Goods;
import top.imzdx.storequeue.redis.RedisUtil;
import top.imzdx.storequeue.tools.GoodsHandle;

import java.util.List;

/**
 * @author Renxing
 * @description
 * @date 2021/4/10 20:41
 */
@Service
public class GoodsService {
    final public int SUCCESS = 200;
    final public int NO_STOCK = 201;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private GoodsDao goodsDao;
    @Autowired
    private GoodsHandle goodsHandle;
    @Autowired
    private SeckillService seckillService;
    @Autowired
    private BuyService buyService;
    @Autowired
    private Producer producer;

    public List<Category> getCategory() {
        return goodsDao.getCategory();
    }

    public List<Goods> getGoods(String category) {
        return goodsHandle.removeGoodsByState(goodsDao.getGoodsByCategory(category));
    }

    public List<Goods> getAllGoods(String category) {
        return goodsDao.getGoodsByCategory(category);
    }

    public Goods getGoods(long gid) {
        Goods goods = (Goods) redisUtil.hget("goods", Long.toString(gid));
        if (goods == null) {
            goods = goodsDao.getGoodsByGid(gid);
            redisUtil.hset("goods", Long.toString(gid), goods);
            if (goods != null && goods.getState() == 1) {
                return null;
            }
        }
        return goods;
    }

    public Seckill getSeckillByGid(int gid) {
        return seckillService.getSeckillByGid(gid);
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
        redisUtil.hdel("goods", Long.toString(gid));
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
        redisUtil.hset("goods", Long.toString(gid), goods);
        return goodsDao.updateGoods(goods);
    }

    public long buyCreate(long gid, long uid) {
        //long[] meg = new long[]{gid, uid};
        Goods goods = getGoods(gid);
        if (goods == null && goods.getState() != 1) {
            return -1;
        }
        JSONArray meg = new JSONArray();
        meg.add(gid);
        meg.add(uid);
        //此处传入系统当前时间戳,用作生成uuid的字段
        long time = System.currentTimeMillis();
        meg.add(time);
        producer.sendMsg("buy.create", meg.toString());
        return time;
    }

    public Goods subStock(Goods goods, int i) {
        goods.setStock(goods.getStock() - i);
        redisUtil.hset("goods", goods.getGid() + "", goods);
        goodsDao.updateGoods(goods);
        return goods;
    }

    public Boolean hasStock(Goods goods) {//是否有库存的方法
        //获取库存
        int stock = goods.getStock();
        //有库存
        return stock > 0;
    }

}
