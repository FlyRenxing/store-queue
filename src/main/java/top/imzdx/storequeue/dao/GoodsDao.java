package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.*;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.pojo.goods.Category;
import top.imzdx.storequeue.pojo.goods.Goods;

import java.util.List;

/**
 * @author Renxing
 * @description
 * @date 2021/4/10 20:41
 */
@Mapper
public interface GoodsDao {
    @Select("SELECT * FROM `category`")
    List<Category> getCategory();

    @Select("select * from goods where category =#{category} or #{category} is null or 0=#{category}")
    List<Goods> getGoodsByCategory(@Param("category") String category);

    @Select("select  *  from  goods order by rand() limit #{n}")
    List<Goods> getGoodsRandom(int n);

    @Select("SELECT * FROM `goods` where gid=#{id}")
    Goods getGoodsById(long id);

    @Insert("insert into goods(gname,price,category,total,stock,state,pic,details,remarks)  values(#{goods.gname},#{goods.price},#{goods.category},#{goods.total},#{goods.stock},#{goods.state},#{goods.pic},#{goods.details},#{goods.remarks})")
    int addgoods(@Param("goods")Goods goods);

    @Delete("delete from goods where gid=#{gid}")
    int deletegoods(@Param("gid")int gid);

}
