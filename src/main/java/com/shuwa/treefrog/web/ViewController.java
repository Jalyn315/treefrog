package com.shuwa.treefrog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 主要是对请求做一个转发操作
 * 同时这里包含不需要拦截的一些请求对应的页面
 */
@Controller
public class ViewController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 传递到index页面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        logger.info("ViewController->index");
        return "index";
    }

    /**
     * 传递到index页面
     *
     * @return
     */
    @RequestMapping("/welcome")
    public String welcome() {
        logger.info("ViewController->welcome");
        return "welcome";
    }


    /**
     * 传递到注册页面
     *
     * @return
     */
    @RequestMapping("/register")
    public String register() {
        logger.info("ViewController->register");
        return "signup";
    }

    /**
     * 传递到login页面
     *
     * @return
     */
    @RequestMapping(value = "/signin")
    public String signin() {
        logger.info("ViewController->signin");
        return "login";
    }



}
