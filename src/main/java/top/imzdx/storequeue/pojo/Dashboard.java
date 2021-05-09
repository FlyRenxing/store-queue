package top.imzdx.storequeue.pojo;

/**
 * @author Renxing
 * @description
 * @date 2021/4/17 19:37
 */
public class Dashboard {
    private int orderNum;
    private double gvm;
    private int goodsNum;
    private int userNum;

    public double getGvm() {
        return gvm;
    }

    public void setGvm(double gvm) {
        this.gvm = gvm;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
