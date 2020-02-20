package com.shuwa.treefrog.config;


import com.shuwa.treefrog.interceptor.UserInterceptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
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
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login.html").setViewName("login");
        registry.addViewController("/user.html").setViewName("user");
        registry.addViewController("/upload").setViewName("upload");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //pringboot已经做好静态资源映射，拦截器不用处理静态资源
        //registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**").excludePathPatterns("/login.html","/","/user_login","/asserts/**");
    }
    public  static final String filepath = "/File/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/File/").addResourceLocations(profile);
    }
}
