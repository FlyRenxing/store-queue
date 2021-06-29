package top.imzdx.storequeue.tools;

import org.springframework.stereotype.Component;
import top.imzdx.storequeue.pojo.goods.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Renxing
 * @description
 * @date 2021/4/11 22:20
 */
@Component
public class GoodsHandle {
    public List<Goods> removeGoodsByState(List<Goods> goods) {
        //删除状态为1（下架）的商品
        ArrayList<Goods> goodsCopy = new ArrayList<>(goods);
        for (int i = goodsCopy.size() - 1; i >= 0; i--) {
            if (goodsCopy.get(i).getState() == 1) {
                goods.remove(i);
            }
        }
        return goods;
    }
}
