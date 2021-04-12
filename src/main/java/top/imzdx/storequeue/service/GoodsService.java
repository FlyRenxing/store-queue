package top.imzdx.storequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.pojo.goods.Category;
import top.imzdx.storequeue.pojo.goods.Goods;

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

    public List<Category> getCategory() {
        return goodsDao.getCategory();
    }

    public List<Goods> getGoods(String category) {
        return goodsDao.getGoodsByCategory(category);
    }

    public Goods getGoods(long id) {
        return goodsDao.getGoodsById(id);
    }

    public List<Goods> getGoodsRandom(int n) {
        return goodsDao.getGoodsRandom(n);
    }
}
