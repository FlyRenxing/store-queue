package top.imzdx.storequeue.pojo.goods;


import java.io.Serializable;

public class Category implements Serializable {

  private long tyid;
  private String tyname;


  public long getTyid() {
    return tyid;
  }

  public void setTyid(long tyid) {
    this.tyid = tyid;
  }


  public String getTyname() {
    return tyname;
  }

  public void setTyname(String tyname) {
    this.tyname = tyname;
  }

}
