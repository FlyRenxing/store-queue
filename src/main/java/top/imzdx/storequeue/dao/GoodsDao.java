package top.imzdx.storequeue.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.imzdx.storequeue.pojo.goods.Category;

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
}
