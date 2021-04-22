package top.imzdx.storequeue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imzdx.storequeue.dao.SeckillDao;
import top.imzdx.storequeue.pojo.Seckill;

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

    public int deleteSeckill(int sid){
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

    public List<Seckill> getAllSeckill(){
        return seckillDao.selectSeckill();
    }

}
