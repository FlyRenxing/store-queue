package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.SeckillDao;
import top.imzdx.storequeue.pojo.Seckill;
import top.imzdx.storequeue.redis.RedisUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


@Service
public class SeckillService {
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private RedisUtil redisUtil;


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

}
