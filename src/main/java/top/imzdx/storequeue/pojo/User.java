package top.imzdx.storequeue.pojo;


public class User {

  private long uid;
  private String uname;
  private String password;
  private String phone;
  private String email;
  private java.sql.Date birthday;
  private java.sql.Timestamp regtime;


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }


  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public java.sql.Date getBirthday() {
    return birthday;
  }

  public void setBirthday(java.sql.Date birthday) {
    this.birthday = birthday;
  }


  public java.sql.Timestamp getRegtime() {
    return regtime;
  }

  public void setRegtime(java.sql.Timestamp regtime) {
    this.regtime = regtime;
  }

}
