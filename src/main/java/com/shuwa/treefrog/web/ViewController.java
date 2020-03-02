package com.shuwa.treefrog.web;

import com.shuwa.treefrog.model.PageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


    /**
     * 传递到 user 页面
     *
     * @return
     */
    @RequestMapping(value = "/user_home")
    public String user() {
        logger.info("ViewController->user");
        return "user";
    }


    /**
     * 控制页面转发到 /admin/login.html 不带数据
     * @return
     */
    @GetMapping(value = "/adminLogin")
    public String adminLogin() {
        logger.info("ViewController->adminLogin");
        return "admin/login";
    }
    /**
     * 控制页面转发到 /admin/userlist.html 不带数据
     * @return
     */
    @RequestMapping(value = "/adminUserList")
    public String adminUserList(Model model) {
        logger.info("ViewController->adminUserList");
        model.addAttribute("page",new PageParam());
        return "admin/userlist";
    }

    /**
     * 控制页面转发到 /admin/typelist.html
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/adminTypeList")
    public String adminTypeList(Model model) {
        logger.info("ViewController->adminTypeList");
        model.addAttribute("page", new PageParam());
        return "admin/typelist";
    }

    /**
     * 控制页面转发到 /admin/filelist.html
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/adminFileList")
    public String adminFileList(Model model) {
        logger.info("ViewController->adminFileList");
        model.addAttribute("page", new PageParam());
        return "admin/filelist";
    }

}
