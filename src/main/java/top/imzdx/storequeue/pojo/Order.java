package top.imzdx.storequeue.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Order implements Serializable {
    @JsonIgnore
    final public int STATE_NOPAY = 0;
    @JsonIgnore
    final public int STATE_ISPAY = 1;
    @JsonIgnore
    final public int STATE_CLOSE = 2;
    private long oid;
    private long uid;
    private long gid;
    private String ordertime;
    private long state;
    private double price;
    private double discount;
    private double pay;
    private String goods_snapshot;
    private String user_snapshot;
    private long sid;

    @Override
    public String toString() {
        return "Order{" +
                "STATE_NOPAY=" + STATE_NOPAY +
                ", STATE_ISPAY=" + STATE_ISPAY +
                ", STATE_CLOSE=" + STATE_CLOSE +
                ", oid=" + oid +
                ", uid=" + uid +
                ", gid=" + gid +
                ", ordertime='" + ordertime + '\'' +
                ", state=" + state +
                ", price=" + price +
                ", discount=" + discount +
                ", pay=" + pay +
                ", goods_snapshot='" + goods_snapshot + '\'' +
                ", user_snapshot='" + user_snapshot + '\'' +
                ", sid=" + sid +
                '}';
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPay() {
        return pay;
    }

    public void setPay(double pay) {
        this.pay = pay;
    }

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

    public void setGoods_snapshot(String goods_snapshot) {
        this.goods_snapshot = goods_snapshot;
    }


    public String getUser_snapshot() {
        return user_snapshot;
    }

    public void setUser_snapshot(String user_snapshot) {
        this.user_snapshot = user_snapshot;
    }

}
