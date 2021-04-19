package top.imzdx.storequeue.pojo;


public class Seckill {

  private long sid;
  private long gid;
  private java.sql.Timestamp starttime;
  private java.sql.Timestamp endtime;
  private String data;


  public long getSid() {
    return sid;
  }

  public void setSid(long sid) {
    this.sid = sid;
  }


  public long getGid() {
    return gid;
  }

  public void setGid(long gid) {
    this.gid = gid;
  }


  public java.sql.Timestamp getStarttime() {
    return starttime;
  }

  public void setStarttime(java.sql.Timestamp starttime) {
    this.starttime = starttime;
  }


  public java.sql.Timestamp getEndtime() {
    return endtime;
  }

  public void setEndtime(java.sql.Timestamp endtime) {
    this.endtime = endtime;
  }


  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

}
