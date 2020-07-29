package com.yd.commom;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 自定义拦截器
 * created by rxy 2020-7-29 15:00:00
 */
@Component
public class Interceptor implements HandlerInterceptor {
    //1. HTTP 1.0 get请求  post请求 还有其他的HTTP 1.1的其他请求方式OPTIONS, PUT, DELETE, TRACE 和 CONNECT

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        System.out.println("preHandler");
        System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod) handler).getMethod().getName());
        httpServletRequest.setAttribute("start", new Date().getTime());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandler");
        Long start = (Long) httpServletRequest.getAttribute("start");
        System.out.println("time interceptor 耗时："+(new Date().getTime()-start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
        Long start = (Long) httpServletRequest.getAttribute("start");
        System.out.println("time interceptor 耗时："+(new Date().getTime()-start));
        System.out.println("ex is:"+e);
    }

}
