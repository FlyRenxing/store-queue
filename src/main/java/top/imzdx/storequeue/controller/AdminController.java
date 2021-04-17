package top.imzdx.storequeue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imzdx.storequeue.dao.AdminDao;
import top.imzdx.storequeue.interceptor.AdminRequired;
import top.imzdx.storequeue.pojo.Dashboard;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;

/**
 * @author Renxing
 * @description
 * @date 2021/4/15 14:43
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminDao adminDao;

    @GetMapping("dashboard")
    @AdminRequired
    public Result getInfo() {
        Dashboard dashboard = new Dashboard();
        dashboard.setGvm(adminDao.getGvm());
        dashboard.setGoodsNum(adminDao.getGoodNum());
        dashboard.setUserNum(adminDao.getUserNum());
        dashboard.setOrderNum(adminDao.getOrderNum());

        return new ResultTools().success("获取成功", dashboard);
    }

}
