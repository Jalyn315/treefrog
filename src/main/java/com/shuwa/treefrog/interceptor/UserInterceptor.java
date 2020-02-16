package com.shuwa.treefrog.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("loginUser");//判断是否有用户已经登入
        if(user == null){
            request.setAttribute("msg","没有权限请先登陆");
            request.getRequestDispatcher("/login.html").forward(request,response);
            return false;
        }else{
            //放行请求
            return  true;
        }
        /***
         *
         * 其他拦截
         */





    }
}
