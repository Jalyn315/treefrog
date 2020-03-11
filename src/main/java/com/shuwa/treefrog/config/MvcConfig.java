package com.shuwa.treefrog.config;


import com.shuwa.treefrog.interceptor.RegisterInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConfigurationProperties(prefix = "file-upload")
public class MvcConfig  implements WebMvcConfigurer {
//    文件上传路径
    private static String profile="G:/treefrog/";

    @Override

    //添加视图控制器
    public void addViewControllers(ViewControllerRegistry registry) {
        //访问路径映射
        //取消了之前的绝对映射
        registry.addViewController("/").setViewName("welcome");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/user.html").setViewName("user");
        registry.addViewController("/upload").setViewName("upload");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //pringboot已经做好静态资源映射，拦截器不用处理静态资源
        //registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**").excludePathPatterns("/login.html","/","/user_login","/asserts/**");
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
    public  static final String filepath = "/File/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/File/").addResourceLocations(profile);
    }
}
