package top.imzdx.storequeue;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.es.GoodsESDao;
import top.imzdx.storequeue.pojo.goods.Goods;
import top.imzdx.storequeue.service.GoodsService;

import java.util.List;

@SpringBootTest
public class ESTest {
    @Autowired
    GoodsESDao goodsESDao;
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    GoodsService goodsService;

    @Test
    public void test() {
//        goodsESDao.deleteAll();
        Goods g1 = new Goods();
        g1.setGname("鲜橙果然多");
        g1.setRemarks("这是个备注一个哦");
        goodsESDao.save(g1);
//        g1.setDetails("平果果炸的！");
//        Goods g2 = new Goods();
//        g2.setGname("鲜橙果然多");
        List<Goods> goodsList = goodsService.search(null, "个", Pageable.ofSize(100));
        System.out.println(goodsList);
    }

}
