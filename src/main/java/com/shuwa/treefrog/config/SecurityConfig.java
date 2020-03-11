package com.shuwa.treefrog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 关于权限相关的都往这里面写
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 此 datasource 加载 application.xml 中的数据库的配置
     */
    @Resource
    private DataSource datasource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

//                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginProcessingUrl("/user_login")
                //.defaultSuccessUrl("/index")
                //记住我-功能添加
//                .and()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me-cookie")
                .tokenValiditySeconds(2 * 24 * 60 * 60)
                .tokenRepository(persistentTokenRepository())
                //关闭自带访问任何请求都验证的功能
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {

        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(datasource);

        return tokenRepository;
    }
}
