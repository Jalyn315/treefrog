package com.shuwa.treefrog.web;

import com.shuwa.treefrog.constant.UserConstant;
import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.exception.LoginException;
import com.shuwa.treefrog.exception.RegisterException;
import com.shuwa.treefrog.service.impl.AdminService;
import com.shuwa.treefrog.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于转发 管理员 相关页面
 */
@Controller
public class AdminController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService; // userervice -> userService
    @Autowired
    private AdminService adminService;
    /**
     * 传递到主页面
     * 主页面
     * @return
     */
    @RequestMapping(value = "/user_list")
    public String userList() {
        logger.info("AdminController->userList");
        return "admin/list";
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/userDelete/{id}")
    public String userDelete(@PathVariable("id") int id) {
        logger.info("AdminController->userDelete");
        //判断用户权限，如果不是管理员，则显示不能删除
        userService.deleteUser(id);
        return "admin/list";
    }

    /**
     * 传递到 users 页面
     *
     * @return
     */
    @GetMapping(value = "/users")
    public String users(Model model) {
        logger.info("AdminController->users");
        List<User> users = userService.getAllUser();
        model.addAttribute("users", users);
        return "admin/list";
    }

    /**
     * 处理 管理员登陆
     * @return
     */
    @PostMapping(value = "admin/adminLoginForm")
    public String adminLoginForm(
            @RequestParam("username") String userName
            ,@RequestParam("password") String password
            ,HttpServletRequest request) {
        logger.info("AdminController->adminLoginForm");
        Map<String,String> loginErrorMap = new HashMap<>();
        try {
            adminService.login(userName,password);
        } catch (LoginException e) {
            loginErrorMap.put("loginError",e.getMessage());
            request.setAttribute("loginErrorMap", loginErrorMap);
            return "admin/login";
        }
        return "admin/list"; //登录成功，到用户管理页面 list.html
    }

    @PostMapping(value = "admin/adminRegisterForm")
    public String adminRegisterForm(
            @RequestParam("username") String userName
            ,@RequestParam("password") String password
            ,@RequestParam("rePassword") String rePassword
            ,HttpServletRequest request) {
        logger.info("AdminController->adminRegisterForm");
        Map<String,String> registerErrorMap = new HashMap<>();
        //进行简单的密码重复验证
        if (password != null && !password.equals(rePassword)) {
            registerErrorMap.put("passwordNotEquals","注册两次密码不相同！");
            return "admin/login";
        }
        try {
            adminService.register(userName,password);
        } catch (RegisterException e) {
            registerErrorMap.put("registerError",e.getMessage());
            request.setAttribute("registerErrorMap",registerErrorMap);
            return "admin/login";
        }
        return "admin/login";
    }
}
