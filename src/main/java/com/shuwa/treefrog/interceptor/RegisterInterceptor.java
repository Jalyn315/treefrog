package com.shuwa.treefrog.interceptor;

import com.shuwa.treefrog.constant.UserConstant;
import com.shuwa.treefrog.service.impl.SmsService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进行注册字段检查
 */
public class RegisterInterceptor implements HandlerInterceptor {
    @Autowired
    private SmsService smsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String phone = request.getParameter("phone");
        String verifiCode = request.getParameter("verifiCode");
        if (smsService == null) {//解决service为null无法注入问题
            System.out.println("RegisterInterceptor->smsService is null!!!");
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
            smsService = (SmsService) factory.getBean("smsService");
        }
        String verifiCode2 = smsService.getValue(phone);
        if (UserConstant.check(userName, password, password2, phone, verifiCode, verifiCode2)) {
            //都通过，则返回true-表示放行
            return true;
        } else {
            //将错误信息返回给页面
            request.setAttribute("errorMap", UserConstant.errorMap);
            request.getRequestDispatcher("register").forward(request, response);
            //验证失败，则转发到signup页面
            //每次(转发后)在使用前将map清空，防止错误信息累积
            if (UserConstant.errorMap != null) {
                UserConstant.errorMap.clear();
            }
            return false;
        } //end if...else

    } //end preHandler()
}
