package top.imzdx.storequeue.result;

/**
 * @author Renxing
 * @description 结果集，通常api返回结果由该类包装
 * @date 2021/4/6 20:11
 */
public class Result {
    private int code;
    private String meg;
    private Object data;
    private long time;

    public Result(int code, String meg, Object data) {
        this.code = code;
        this.meg = meg;
        this.data = data;
        this.time = System.currentTimeMillis();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMeg() {
        return meg;
    }

    public void setMeg(String meg) {
        this.meg = meg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
