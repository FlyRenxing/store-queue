//package top.imzdx.storequeue;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch.core.IndexRequest;
//import co.elastic.clients.elasticsearch.core.IndexResponse;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import co.elastic.clients.elasticsearch.core.search.Hit;
//import co.elastic.clients.elasticsearch.core.search.TotalHits;
//import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
//import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
//import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
//import com.alibaba.fastjson.JSON;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import top.imzdx.storequeue.pojo.User;
//import top.imzdx.storequeue.pojo.goods.Goods;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Logger;
//
//@SpringBootTest
//class StoreQueueApplicationTests {
//
//    @Autowired
//    private ElasticsearchClient elasticsearchClient;
//    @Test
//    public void addGoods() throws IOException {
//        Goods goods = new Goods();
//        goods.setGid(2);
//        goods.setGname("苹果手机");
//        elasticsearchClient.index(i->{
//            i.index("goods");
//            i.document(goods);
//            return i;
//        });
//    }
//
//    @Test
//    public void search() throws Exception{
//        List<Goods> tProductList = new ArrayList<>();
//
//        SearchResponse<Goods> response = elasticsearchClient.search(
//                s -> s.index("goods")
//                        .query(
//                                q -> q.match(
//                                        t -> t.field("gname")
//                                                .query("手机")
//                                )
//                        ),
//                Goods.class
//        );
//        TotalHits total = response.hits().total();
//        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;
//        if (isExactResult) {
//            System.out.println("There are "+total.value()+" results" );
//        } else {
//            System.out.println("There are more than "+total.value()+" results" );
//        }
//        List<Hit<Goods>> hits = response.hits().hits();
//        for (Hit<Goods> hit : hits) {
//            Goods goods = hit.source();
//            tProductList.add(goods);
//        }
//        System.out.println(tProductList);
//    }
//
//}
