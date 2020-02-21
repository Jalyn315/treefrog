package com.shuwa.treefrog.web;

import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    UserService userervice;

    @GetMapping("/login")
    public String toLoginPage(){
        return "login";
    }


    @RequestMapping("/user_login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password
                        , Map<String,Object> map, HttpSession session){

        User user = userervice.login(username,password);
        if(user != null){
            session.setAttribute("loginUser",username);  //登录成功将用户名存入会话对象
            session.setAttribute("userId",user.getId());  //存入用户ID
            session.setAttribute("user",user);
            map.put("user",user);
            return "index";
        }else {
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }


    /**
     * 注销登录
     * 重定向前台页面。清空session中的缓存
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("loginUser");
        session.removeAttribute("userId");
        return "redirect:/login.html"; //返回主页
    }



}
