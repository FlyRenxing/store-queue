package top.imzdx.storequeue.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.SeckillDao;
import top.imzdx.storequeue.pojo.Seckill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class SeckillService {
    @Autowired
    private SeckillDao seckillDao;

    public int newSeckill(int gid, String startday, String starttime, String endday, String endtime, String data) {
        Seckill seckillByGid = seckillDao.selectSeckillByGid(gid);
        if (seckillByGid.getGid() == gid) {
            return -1;
        }
        Seckill seckill = new Seckill();
        seckill.setGid(gid);
        seckill.setStartday(startday);
        seckill.setStarttime(starttime);
        seckill.setEndday(endday);
        seckill.setEndtime(endtime);
        seckill.setData(data);
        seckill.setUsecount(0);
        return seckillDao.insertSeckill(seckill);
    }

    public int deleteSeckill(int sid) {
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
        return seckillDao.updateSeckill(seckill);
    }

    public int editSeckill(Seckill seckill) {
        return seckillDao.updateSeckill(seckill);
    }

    public List<Seckill> getAllSeckill() {
        return seckillDao.selectSeckill();
    }

    public Seckill getSeckillByGid(int gid) {
        return seckillDao.selectSeckillByGid(gid);
    }

    public int editUseCountByGid(int gid) {
        Seckill seckill = getSeckillByGid(gid);
        seckill.setUsecount(seckill.getUsecount() + 1);
        return editSeckill(seckill);
    }

    public boolean isSeckill(Seckill seckill) {
        if (seckill == null) {
            return false;
        } else {

            return true;
        }
    }

    public boolean isSeckillTime(Seckill seckill) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long start = 0, end = 0, now = 0;
        try {
            start = sdf.parse(seckill.getStartday() + " " + seckill.getStarttime()).getTime();
            end = sdf.parse(seckill.getEndday() + " " + seckill.getEndtime()).getTime();
            now = new Date().getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //以上为获取秒杀时间等步骤

        if (seckill != null && start < now && now < end) {//如果该商品为秒杀商品且在活动期间内
            return true;
        } else {
            return false;
        }
    }

    public boolean isSeckillRange(Seckill seckill) {
        JSONArray jsonArray = JSONArray.parseArray(seckill.getData());//把data字段里的东西变成一个数组
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);//??看不懂
            if (seckill.getUsecount() >= jsonObject.getInteger("top") && seckill.getUsecount() < jsonObject.getInteger("end")) {//控制已享受折扣人数在该范围区间
                return true;
            }

        }
        return false;

    }

    public double getRangeDiscount(Seckill seckill) {
        JSONArray jsonArray = JSONArray.parseArray(seckill.getData());//把data字段里的东西变成一个数组
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (seckill.getUsecount() >= jsonObject.getInteger("top") && seckill.getUsecount() <= jsonObject.getInteger("end")) {//控制已享受折扣人数在该范围区间
                return jsonObject.getDouble("discount");
            }

        }
        return 1;
    }

}
