package top.imzdx.storequeue.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import top.imzdx.storequeue.pojo.goods.Goods;

import java.util.List;

public interface GoodsESDao extends ElasticsearchRepository<Goods, Long> {
    List<Goods> findByGname(String name);

    List<Goods> findByDetails(String details);

    List<Goods> findByRemarks(String remarks);

    List<Goods> findByGnameOrDetailsOrRemarks(String gname, String details, String remarks);


}

