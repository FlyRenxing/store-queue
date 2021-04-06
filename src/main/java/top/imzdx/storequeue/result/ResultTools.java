package top.imzdx.storequeue.result;

/**
 * @author Renxing
 * @description 结果集工具类，通过它生成结果集
 * @date 2021/4/6 20:13
 */
public class ResultTools {
    public Result success(String meg, Object data) {
        return new Result(200, meg, data);
    }

    public Result fail(int code, String meg, Object data) {
        return new Result(code, meg, data);
    }
}
