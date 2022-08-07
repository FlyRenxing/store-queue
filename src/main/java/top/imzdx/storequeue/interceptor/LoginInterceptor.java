package top.imzdx.storequeue.interceptor;

import com.alibaba.fastjson.JSONObject;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import top.imzdx.storequeue.pojo.User;
import top.imzdx.storequeue.result.Result;
import top.imzdx.storequeue.result.ResultTools;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author Renxing
 * @description
 * @date 2021/4/11 13:44
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // ①:START 方法注解级拦截器
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 这写你拦截需要干的事儿，比如取缓存，SESSION，权限判断等
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return true;
            } else {
                Result result = new ResultTools().fail(299, "当前未登录", null);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                try {
                    String json = JSONObject.toJSONString(result);
                    out = response.getWriter();
                    out.append(json);
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(500);
                    return false;
                }
            }

        }
        return true;


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
