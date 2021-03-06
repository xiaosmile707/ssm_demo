package com.hp.ssm.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (checkURL(request.getRequestURI())) {
            return true;
        }
        if (request.getRequestURI().startsWith("/admin") && request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin/login");
            return false;
        }
        if (request.getRequestURI().startsWith("/user") &&request.getSession().getAttribute("user") == null && request.getSession().getAttribute("email") == null) {
            response.sendRedirect("/user/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    private boolean checkURL(String url) {
        if (url.startsWith("/img") || url.startsWith("/error")) {
            return true;
        }
        switch (url) {
            case "/user/login":
            case "/user/register":
            case "/user/reset":
            case "/user/validate":
            case "/user/home":
            case "/user/index":
            case "/admin/login":
            case "/admin/register":
            case "/admin/reset":
            case "/admin/validate":
            case "/home":
                return true;
            default:
                return false;
        }
    }
}
