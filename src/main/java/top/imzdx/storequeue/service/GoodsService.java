package top.imzdx.storequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.dao.SeckillDao;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.goods.Category;
import top.imzdx.storequeue.pojo.goods.Goods;
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

    public List<Category> getCategory() {
        return goodsDao.getCategory();
    }

    public List<Goods> getGoods(String category) {
        return goodsHandle.removeGoodsByState(goodsDao.getGoodsByCategory(category));
    }

    public List<Goods> getAllGoods(String category) {
        return goodsDao.getGoodsByCategory(category);
    }

    public Goods getGoods(long id) {
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
}
