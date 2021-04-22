package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.GoodsDao;
import top.imzdx.storequeue.dao.SeckillDao;
import top.imzdx.storequeue.pojo.Order;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Category;
import top.imzdx.storequeue.pojo.goods.Goods;
import top.imzdx.storequeue.tools.GoodsHandle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private SeckillService seckillService;

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

    public int  buy(long gid,long uid){//uid需要从controller类获取！
        Goods goods = goodsDao.getGoodsById(gid);
        int stock = goods.getStock();//先获取库存
        Order order=new Order();//实例化一个order表
        User user=userService.findUserByUid(uid);
        user.setPassword("****");
        if (stock>0) {//如果有库存的话，就判断他是否为秒杀商品，如果是就向order表添加数据

            Seckill seckill = seckillService.getSeckillByGid(Integer.parseInt(String.valueOf(gid)));//获得了该商品，存入seckill表里
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long start = 0, end = 0, now = 0;
            try {
                start = sdf.parse(seckill.getStartday() + " " + seckill.getStarttime()).getTime();
                end = sdf.parse(seckill.getEndday() + " " + seckill.getEndtime()).getTime();
                now = new Date().getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (seckill != null && start < now && now < end) {//如果该商品为秒杀商品且在活动期间内
                //获得data字段的json数据，控制折扣 先useCount+1 再找到他应该享受的折扣top<useCount<end 再计算价格jsonObject.discount*goods.price=order.pay
                JSONArray jsonArray = JSONArray.parseArray(seckill.getData());

                seckill.setUsecount(seckill.getUsecount() + 1);//享受折扣人数+1
                seckillService.editSeckill(seckill);
                for (int i = 0; i < jsonArray.size(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);//??看不懂
                    if (seckill.getUsecount() >= jsonObject.getInteger("top") && seckill.getUsecount() <= jsonObject.getInteger("end")) {//控制已享受折扣人数在该范围区间
                        //创建订单 调用orderservice 完善order表
                        order.setDiscount(jsonObject.getDouble("discount"));//来自jsonObject里 符合人数区间
                        order.setPay(order.getDiscount() * goods.getPrice());//来自刚刚存入的order里的discount * goods表里的原价
                        break;
                    }
                    if (i == jsonArray.size() && jsonObject.getInteger("end") < seckill.getUsecount()) {
                        //如果最后一个end仍然小于usecount那么按正常价格
                        order.setDiscount(1);
                        order.setPay(goods.getPrice());
                    }
                }
            }else{
                order.setDiscount(1);
                order.setPay(goods.getPrice());
            }

            order.setUid(uid);
            order.setGid(gid);
            order.setPrice(goods.getPrice());//查goods表该商品的价格，保证统一


            String goods_snapshot= JSON.toJSONString(goods);//运用了json
            order.setGoods_snapshot(goods_snapshot);//把他存入order实体类表里

            String user_snapshot=JSON.toJSONString(user);
            order.setUser_snapshot(user_snapshot);//同上

            goods.setStock(goods.getStock() - 1);// 改变goods表的库存数据
            goodsDao.updateGoods(goods);//减库存

            //直接返回操作order表的结果
            System.out.println(order);
            return orderService.newOrder(order);
        }else{
            return -1;//返回-1表示无库存
        }

    }
}
