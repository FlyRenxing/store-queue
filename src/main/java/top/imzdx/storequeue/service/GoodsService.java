package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSONArray;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.mq.Producer;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.goods.Category;
import top.imzdx.storequeue.pojo.goods.Goods;
import top.imzdx.storequeue.redis.RedisUtil;
import top.imzdx.storequeue.tools.GoodsHandle;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

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
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

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
        if (goods == null || goods.getState() == 1) {
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

    public List<Goods> search(Integer cid, String keyword, Pageable pageable) {

// <1> 创建 NativeSearchQueryBuilder 对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
// <2.1> 筛选条件 cid
        if (cid != null) {
            nativeSearchQueryBuilder.withFilter(QueryBuilders.termQuery("cid", cid));
        }
// <2.2> 筛选
        if (StringUtils.hasText(keyword)) {
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = { // TODO 芋艿，分值随便打的
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchQuery("gname", keyword),
                            ScoreFunctionBuilders.weightFactorFunction(10)),
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchQuery("details", keyword),
                            ScoreFunctionBuilders.weightFactorFunction(2)),
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchPhraseQuery("remarks", keyword),
                            ScoreFunctionBuilders.weightFactorFunction(3)),
//                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchQuery("description", keyword),
//                            ScoreFunctionBuilders.weightFactorFunction(2)), // TODO 芋艿，目前这么做，如果商品描述很长，在按照价格降序，会命中超级多的关键字。
            };
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(functions)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM) // 求和
                    .setMinScore(2F); // TODO 芋艿，需要考虑下 score
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
// 排序
        if (StringUtils.hasText(keyword)) { // <3.1> 关键字，使用打分
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        } else if (pageable.getSort().isSorted()) { // <3.2> 有排序，则进行拼接
            pageable.getSort().get().forEach(sortField -> nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField.getProperty())
                    .order(sortField.getDirection().isAscending() ? SortOrder.ASC : SortOrder.DESC)));
        } else { // <3.3> 无排序，则按照 ID 倒序
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));
        }
// <4> 分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())); // 避免
// <5> 执行查询

        List<SearchHit<Goods>> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Goods.class)
                .getSearchHits();
        System.out.println("searchHits = " + searchHits);
        // <6> 返回结果
        return searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

}
