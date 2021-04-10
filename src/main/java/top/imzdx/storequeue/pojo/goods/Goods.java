package top.imzdx.storequeue.pojo.goods;


public class Goods {

  private long gid;
  private String gname;
  private double price;
  private long category;
  private long total;
  private long stock;
  private String state;
  private String details;
  private String remarks;


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


  public long getStock() {
    return stock;
  }

  public void setStock(long stock) {
    this.stock = stock;
  }


  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
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
