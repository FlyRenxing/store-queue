package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.SeckillDao;
import top.imzdx.storequeue.pojo.Order;
import top.imzdx.storequeue.pojo.SeckillOrderList;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.redis.RedisUtil;
import top.imzdx.storequeue.tools.ExcelUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ExcelUtil excelUtil;


    public int newSeckill(int gid, String startDay, String startTime, String endDay, String endTime, String data) {
        Seckill seckillByGid = seckillDao.selectSeckillByGid(gid);
        if (seckillByGid.getGid() == gid) {
            return -1;
        }
        Seckill seckill = new Seckill();
        seckill.setGid(gid);
        seckill.setStartday(startDay);
        seckill.setStarttime(startTime);
        seckill.setEndday(endDay);
        seckill.setEndtime(endTime);
        seckill.setData(data);
        seckill.setUsecount(0);
        redisUtil.hset("seckill", Long.toString(gid), seckill);
        return seckillDao.insertSeckill(seckill);
    }

    public int deleteSeckill(long sid) {
        redisUtil.hdel("seckill", Long.toString(sid));
        return seckillDao.deleteSeckill(sid);
    }

    public int editSeckill(int sid, int gid, String startday, String starttime, String endday, String endtime, String data, String usecount) {
        Seckill seckill = new Seckill();
        seckill.setSid(sid);
        seckill.setGid(gid);
        seckill.setStartday(startday);
        seckill.setStarttime(starttime);
        seckill.setEndday(endday);
        seckill.setEndtime(endtime);
        seckill.setData(data);
        seckill.setUsecount(Long.parseLong(usecount));
        redisUtil.hset("seckill", Long.toString(seckill.getSid()), seckill);
        return seckillDao.updateSeckill(seckill);
    }

    public int editSeckill(Seckill seckill) {
        redisUtil.hset("seckill", Long.toString(seckill.getGid()), seckill);
        return seckillDao.updateSeckill(seckill);
    }

    public List<Seckill> getAllSeckill() {
        return seckillDao.selectSeckill();
    }

    public Seckill getSeckillByGid(long gid) {
        Seckill seckill = (Seckill) redisUtil.hget("seckill", Long.toString(gid));
        if (seckill == null) {
            seckill = seckillDao.selectSeckillByGid(gid);
            redisUtil.hset("seckill", Long.toString(gid), seckill);
        }
        return seckill;
    }

    public boolean isSeckillTime(Seckill seckill) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long start = 0, end = 0, now = 0;
        try {
            start = sdf.parse(seckill.getStartday() + " " + seckill.getStarttime()).getTime();
            end = sdf.parse(seckill.getEndday() + " " + seckill.getEndtime()).getTime();
            now = System.currentTimeMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //以上为获取秒杀时间等步骤

        return seckill != null && start < now && now < end;
    }

    public boolean isSeckillRange(Seckill seckill) {
        //把data字段里的东西变成一个数组
        JSONArray jsonArray = JSONArray.parseArray(seckill.getData());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (seckill.getUsecount() >= jsonObject.getInteger("top") && seckill.getUsecount() <= jsonObject.getInteger("end")) {
                return true;
            }

        }
        return false;

    }

    public double getRangeDiscount(Seckill seckill) {
        JSONArray jsonArray = JSONArray.parseArray(seckill.getData());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (seckill.getUsecount() >= jsonObject.getInteger("top") && seckill.getUsecount() <= jsonObject.getInteger("end")) {
                return jsonObject.getDouble("discount");
            }

        }
        return 1;
    }

    public HSSFWorkbook getSeckillOrderList(long sid) {
        //此处为Map内的List
        List<SeckillOrderList> orders = new ArrayList<SeckillOrderList>();
        List<Order> orderList = orderService.getPublicListBySid(sid);
        Map<String, List<String>> map = new HashMap<String, List<String>>(orderList.size());


            SeckillOrderList order = new SeckillOrderList();
            User user = null;
            StringBuffer name = new StringBuffer();
            StringBuffer phone = new StringBuffer();
            double discount =0;
            for (int i = 0; i < orderList.size(); i++) {
                JSONObject json = JSONObject.parseObject(orderList.get(i).getUser_snapshot());
                user = JSONObject.toJavaObject(json,User.class);
                name.setLength(0);
                name = name.append(user.getUname());
                phone.setLength(0);
                phone = phone.append(user.getPhone());
                discount = orderList.get(i).getDiscount();
                ArrayList<String> members = new ArrayList<String>();
                members.add(name + "");
                members.add(phone+"");
                members.add(discount + "");
                map.put(orderList.get(i).getOid() + "", members);
            }
        String[] strArray = {"订单ID", "客户名称", "客户电话", "折扣"};

        HSSFWorkbook wb = excelUtil.createExcel(map,strArray);
        return wb;
    }

}
