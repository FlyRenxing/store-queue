package top.imzdx.storequeue.pojo;


public class Seckill {

  private long sid;
  private long gid;
  private String startday;
  private String starttime;
  private String endday;
  private String endtime;
  private String data;
  private long usecount;

  public long getUsecount() {
    return usecount;
  }

  public void setUsecount(long usecount) {
    this.usecount = usecount;
  }

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


  public String getStartday() {
    return startday;
  }

  public void setStartday(String startday) {
    this.startday = startday;
  }


  public String getStarttime() {
    return starttime;
  }

  public void setStarttime(String starttime) {
    this.starttime = starttime;
  }


  public String getEndday() {
    return endday;
  }

  public void setEndday(String endday) {
    this.endday = endday;
  }


  public String getEndtime() {
    return endtime;
  }

  public void setEndtime(String endtime) {
    this.endtime = endtime;
  }


  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

}
