package top.imzdx.storequeue.pojo.goods;


public class Order {

  private long oid;
  private long uid;
  private long adid;
  private long gid;
  private java.sql.Timestamp ordertime;
  private long state;
  private double price;


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


  public long getAdid() {
    return adid;
  }

  public void setAdid(long adid) {
    this.adid = adid;
  }


  public long getGid() {
    return gid;
  }

  public void setGid(long gid) {
    this.gid = gid;
  }


  public java.sql.Timestamp getOrdertime() {
    return ordertime;
  }

  public void setOrdertime(java.sql.Timestamp ordertime) {
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

}
