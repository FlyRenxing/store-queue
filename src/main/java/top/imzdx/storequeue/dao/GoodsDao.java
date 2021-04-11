package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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

    @Select("select * from goods where category =#{category} or #{category} is null")
    List<Goods> getGoodsBycategoory(@Param("category")String category);


}
