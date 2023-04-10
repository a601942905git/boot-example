package com.boot.example;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * com.boot.example.WebRequestInterceptor
 *
 * @author lipeng
 * @date 2019/10/27 下午11:02
 */
@Slf4j
public class WebRequestInterceptor implements HandlerInterceptor {

    /**
     * 业务前置处理
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("开始鉴权");
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write("请先登录！！！");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 业务后置处理，只有preHandle返回true，该方法才会执行
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("只有preHandle返回true，该方法才会执行，此方法在业务方法执行完成之后执行");
    }

    /**
     * 用于资源释放，不管preHandle返回true或者false，该方法都会执行
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("不管preHandle返回true或者false，该方法都会执行，用于释放资源");
    }
}
