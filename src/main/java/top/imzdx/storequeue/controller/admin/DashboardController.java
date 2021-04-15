package top.imzdx.storequeue.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.interceptor.AdminRequired;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;

/**
 * @author Renxing
 * @description
 * @date 2021/4/15 14:43
 */
@RestController
@RequestMapping("/admin/dashboard")
@AdminRequired
public class DashboardController {
    @GetMapping("/")
    public Result getInfo() {
        return new ResultTools().success("获取成功", null);
    }

}
