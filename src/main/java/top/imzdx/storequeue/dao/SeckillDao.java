package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.*;
import top.imzdx.storequeue.pojo.Seckill;

import java.util.List;

@Mapper
public interface SeckillDao {

    @Insert("INSERT INTO `seckill`(`gid`, `startdate`, `starttime`, `enddate`, `endtime`, `data`) VALUES (#{seckill.gid}, #{seckill.startdate}, #{seckill.starttime}, #{seckill.enddate} , #{seckill.endtime}, #{seckill.data})")
    int  insertSeckill(@Param("seckill")Seckill seckill);

    @Delete("delete from seckill where sid=#{sid}")
    int deleteSeckill(@Param("sid")int sid);

    @Update("UPDATE seckill SET gid=#{seckill.gid},starttime=#{seckill.starttime},startdate=#{seckill.startdate},endtime=#{seckill.endtime},enddate=#{seckill.enddate},data=#{seckill.data} WHERE sid = #{seckill.sid}")
    int updateSeckill(@Param("seckill")Seckill seckill);

    @Select("select * from seckill")
    List<Seckill> selectSeckill();
}
