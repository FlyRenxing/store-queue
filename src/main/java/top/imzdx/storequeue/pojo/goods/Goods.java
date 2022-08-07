package top.imzdx.storequeue.pojo.goods;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
@Document(indexName = "goods")
public class Goods implements Serializable {
  @Id
  private long gid;
  @Field(type = FieldType.Text,searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
  private String gname;
  private double price;
  private long category;
  private long total;
  private int stock;
  private int state;
  private String pic;
  @Field(type = FieldType.Text, searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
  private String details;
  @Field(type = FieldType.Text,searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
  private String remarks;

  @Override
  public String toString() {
    return "Goods{" +
            "gid=" + gid +
            ", gname='" + gname + '\'' +
            ", price=" + price +
            ", category=" + category +
            ", total=" + total +
            ", stock=" + stock +
            ", state=" + state +
            ", pic='" + pic + '\'' +
            ", details='" + details + '\'' +
            ", remarks='" + remarks + '\'' +
            '}';
  }

  public long getGid() {
    return gid;
  }

  public void setGid(long gid) {
    this.gid = gid;
  }


  public String getGname() {
    return gname;
  }

  public void setGname(String gname) {
    this.gname = gname;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public long getCategory() {
    return category;
  }

  public void setCategory(long category) {
    this.category = category;
  }


  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }


  public int getStock() {
    return stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }


  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }


  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }


  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }


  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

}
