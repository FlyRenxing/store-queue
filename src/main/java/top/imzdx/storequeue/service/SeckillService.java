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

    public int newSeckill(int gid,String startdate,String starttime,String enddate,String endtime,String data){
        Seckill seckill=new Seckill();
        seckill.setGid(gid);
        seckill.setStartdate(startdate);
        seckill.setStarttime(starttime);
        seckill.setEnddate(enddate);
        seckill.setEndtime(endtime);
        seckill.setData(data);
        return seckillDao.insertSeckill(seckill);
    }

    public int deleteSeckill(int sid){
        return seckillDao.deleteSeckill(sid);
    }

    public  int editSeckill(int sid,int gid,String startdate,String starttime,String enddate,String endtime,String data){
        Seckill seckill=new Seckill();
        seckill.setSid(sid);
        seckill.setGid(gid);
        seckill.setStartdate(startdate);
        seckill.setStarttime(starttime);
        seckill.setEnddate(enddate);
        seckill.setEndtime(endtime);
        seckill.setData(data);
        return seckillDao.updateSeckill(seckill);
    }

    public List<Seckill> getAllSeckill(){
        return seckillDao.selectSeckill();
    }

}
