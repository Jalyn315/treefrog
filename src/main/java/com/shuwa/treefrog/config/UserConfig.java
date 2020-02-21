package com.shuwa.treefrog.config;


import com.shuwa.treefrog.interceptor.RegisterInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
@Configuration
public class UserConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        //显示用户信息时，得先登录或者注册
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(
//                "/updatePage/",
//                "/userUpdate",
//                "/userDelete/",
//                "/logout",
//                "/user",
//                "/users",
//                "/main.html");
//        //防止用户重复提交，注册拦截器
//        registry.addInterceptor(new AvoidDuplicateSubmissionInterceptor()).addPathPatterns("/signup");
        //对注册页面过来的请求进行字段验证
        registry.addInterceptor(new RegisterInterceptor()).addPathPatterns("/signup");

//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/index", "/signin", "/register","/signup","/login");
    }
}
