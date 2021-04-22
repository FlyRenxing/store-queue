package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.*;
import top.imzdx.storequeue.pojo.Seckill;

import java.util.List;

@Mapper
public interface SeckillDao {

    @Insert("INSERT INTO `seckill`(`gid`, `startday`, `starttime`, `endday`, `endtime`, `data`) VALUES (#{seckill.gid}, #{seckill.startday}, #{seckill.starttime}, #{seckill.endday} , #{seckill.endtime}, #{seckill.data})")
    int insertSeckill(@Param("seckill") Seckill seckill);

    @Delete("delete from seckill where sid=#{sid}")
    int deleteSeckill(@Param("sid") int sid);

    @Update("UPDATE seckill SET gid=#{seckill.gid},starttime=#{seckill.starttime},startday=#{seckill.startday},endtime=#{seckill.endtime},endday=#{seckill.endday},data=#{seckill.data},usecount=#{seckill.usecount} WHERE sid = #{seckill.sid}")
    int updateSeckill(@Param("seckill") Seckill seckill);

    //@Select("select * from seckill")
    @Select("SELECT goods.gname, seckill.* FROM goods INNER JOIN seckill ON goods.gid = seckill.gid")
    List<Seckill> selectSeckill();

    @Select("select * from seckill where gid=#{gid}")
    Seckill selectSeckillByGid(@Param("gid") int gid);
}
