package top.imzdx.storequeue.pojo.goods;


public class Order {

  private long oid;
  private long uid;
  private long gid;
  private String ordertime;
  private long state;
  private double price;
  private String goods_snapshot;
  private String user_snapshot;


  public long getOid() {
    return oid;
  }

  public void setOid(long oid) {
    this.oid = oid;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public long getGid() {
    return gid;
  }

  public void setGid(long gid) {
    this.gid = gid;
  }


  public String getOrdertime() {
    return ordertime;
  }

  public void setOrdertime(String ordertime) {
    this.ordertime = ordertime;
  }


  public long getState() {
    return state;
  }

  public void setState(long state) {
    this.state = state;
  }


  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }


  public String getGoods_snapshot() {
    return goods_snapshot;
  }

  public void setGoods_snapshot(String goodsSnapshot) {
    this.goods_snapshot = goodsSnapshot;
  }


  public String getUser_snapshot() {
    return user_snapshot;
  }

  public void setUser_snapshot(String user_snapshot) {
    this.user_snapshot = user_snapshot;
  }

}
