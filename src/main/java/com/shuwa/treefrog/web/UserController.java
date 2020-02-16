package com.shuwa.treefrog.web;

import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService userervice;
    /**
     * 编辑个人信息
     * @return 用户修改页面
     */
    @GetMapping("/updatePage")
    public String toChangePage(){
        return "user";
    }

    /**
     * 提交修改
     * @param user
     * @return
     */
    @PostMapping("/updateEmp")
    public String update(User user,HttpSession session,Model model){

        User u = (User) session.getAttribute("user");
        user.setId(u.getId());
        if( userervice.update(user)){
            session.setAttribute("user", userervice.get(u.getId()));
            model.addAttribute("message","修改信息成功");
        }
        else {
            model.addAttribute("message","修改信息失败");
        }
        return "user";
    }



}
