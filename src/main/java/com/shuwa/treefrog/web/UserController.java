package com.shuwa.treefrog.web;

import com.shuwa.treefrog.constant.UserConstant;
import com.shuwa.treefrog.entity.User;
import com.shuwa.treefrog.model.PageParam;
import com.shuwa.treefrog.service.impl.SmsService;
import com.shuwa.treefrog.service.impl.UserService;
import com.shuwa.treefrog.util.slidingcodeutils.GeeTestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    UserService userService; // userervice -> userService

    @Autowired
    private User user; //这里给 User.java 头部加了个 @Component 修饰符

//    @Autowired
//    private SmsSDKDemo smsSDKDemo; //控制短信服务的功能类

    @Autowired
    private SmsService smsService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 编辑个人信息 （不使用）
     *
     * @return 用户修改页面
     */
    @GetMapping("/updatePage")
    public String toChangePage() {
        logger.info("UserController->toChangePage");
        return "user";
    }

    /**
     * 提交修改   （不使用）
     *
     * @param user
     * @return
     */
    @PostMapping("/updateEmp")
    public String update(User user, HttpSession session, Model model) {
        logger.info("UserController->update");
        User u = (User) session.getAttribute("user");
        user.setId(u.getId());
        if (userService.update(user)) {
            session.setAttribute("user", userService.get(u.getId()));
            model.addAttribute("message", "修改信息成功");
        } else {
            model.addAttribute("message", "修改信息失败");
        }
        return "user";
    }

    @PostMapping("/updateUserInfo")
    @ResponseBody
    public String updateUserInfo(User user) {
        String msg = "";
        if (userService.update(user)) {
            msg = "修改成功！";
        } else {
            msg = "修改失败！请联系管理员!";
        }
        return msg;
    }


    /**
     * 登陆
     *
     * @return
     */
//    @GetMapping("/login")
//    public String toLoginPage() {
//        logger.info("UserController->toLoginPage");
//        return "login";
//    }

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @param map
     * @param session
     * @return
     */
    @RequestMapping("/user_login")
    public String login(@RequestParam("username") String username
            , @RequestParam("password") String password
            , Map<String, Object> map, HttpSession session
            , String geetest_challenge, String geetest_validate, String geetest_seccode) {
        if (!GeeTestUtil.validate(session, geetest_challenge, geetest_validate, geetest_seccode)) {
            map.put("result", "验证失败!!!");
            return "login";
        }
        User user = userService.login(username, password);
        if (user != null) {
            session.setAttribute("loginUser", username);  //登录成功将用户名存入会话对象
            session.setAttribute("userId", user.getId());  //存入用户ID
            session.setAttribute("user", user);
            map.put("user", user);
            //第一次进入主页初始化分页
            map.put("page", new PageParam());
            return "redirect:/index";
        } else {
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }


    /**
     * 注销登录
     * 重定向前台页面。清空session中的缓存
     *
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.removeAttribute("loginUser");
        session.removeAttribute("userId");
        return "redirect:login"; //返回主页
    }


    /**
     * 用户注册功能
     *
     * @param userName
     * @param password
     * @param phone
     * @param request
     * @return
     */
//    @AvoidDuplicateSubmission(needSaveToken = true) //避免重复提交表单的注解
    @PostMapping(value = "/signup")
    public String signup(@RequestParam("userName") String userName
            , @RequestParam("password") String password
            , @RequestParam("phone") String phone
            , HttpServletRequest request) {
        logger.info("UserController->signup");
        boolean isUserNameDup = userService.isUserNameExist(userName);
        boolean isPhoneDup = userService.isPhoneExist(phone);
        //判断用户名是否重复
        if (isUserNameDup) {
            UserConstant.errorMap.put("userNameDup", "用户名已存在！");
        }
        //判断手机号是否重复
        if (isPhoneDup) {
            UserConstant.errorMap.put("phoneDup", "手机号已注册！");
        }
        request.setAttribute("errorMap", UserConstant.errorMap);
        if (isUserNameDup || isPhoneDup) {
            return "signup";
        } else {
            user.setUsername(userName);
            user.setPassword(password);
            user.setPhone(phone);
            userService.register(user);
            return "login";
        } //end if...else
    } //end signup()

    /**
     * 传递到 邮件发送器
     * 主页面
     *
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/sms")
    public String smsSender(HttpServletRequest request) {
        logger.info("UserController->smsSender");
        String phoneNum = request.getParameter("phone");
        String verifyCode = smsService.sendSms(phoneNum);
        if ("empty".equals(verifyCode)) {
            UserConstant.errorMap.put("codeDup", "验证码已发送，请30s后再点击发送！");
            return "手机号码为空，请输入手机号码！";
        }
        return verifyCode;
    }

    /**
     * 编辑个人信息
     * [https://blog.csdn.net/leo3070/article/details/81046383]
     *
     * @return 用户修改页面
     */
    @RequestMapping("/userUpdate{id}")
    public String userUpdate(@PathVariable("id") Integer id
            , Model model
            , HttpSession session) {
        logger.info("UserController->userUpdate");
        User user = userService.get(id);
        session.setAttribute("userId", user.getId());
        model.addAttribute("user", user);
        userService.update(user);
        return "admin/userUpdate";
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public User userInfo(Integer id) {
        return userService.get(id);
    }


    /**
     * 判断原密码是否正确
     *
     * @param passwordAgo
     * @param id
     * @return
     */
    @PostMapping("/verify_passwordAgo")
    @ResponseBody
    public String verifyPasswordAgo(String passwordAgo, Integer id) {
        String msg = "";
        if (userService.verifyPassword(passwordAgo, id)) {
            msg = "true";
        } else {
            msg = "原密码不正确！请重新输入";
        }
        return msg;
    }

    /**
     * 根据id修改用户密码
     *
     * @param password
     * @param id
     * @return
     */
    @PostMapping("/resetPassword")
    @ResponseBody
    public String resetPassword(String password, Integer id) {
        String msg = "";
        if (!"".equals(password)) {
            if (userService.resetassword(password, id)) {
                msg = "修改成功! ";
            } else {
                msg = "修改失败!";
            }
        } else {
            msg = "密码不能为空!";
        }
        return msg;
    }

    @PostMapping("/update/via")
    @ResponseBody
    public String updateVia(@RequestParam("file") MultipartFile file, HttpSession session) {
        try {
            userService.uploadUserVia(file, Integer.parseInt(session.getAttribute("userId").toString()));
        } catch (Exception e) {
            System.out.println(e + "5555");
        }
        return "上传成功";
    }

    @GetMapping("/via")
    @ResponseBody
    public String getvia(HttpSession session) {
        return userService.getVia(Integer.parseInt(session.getAttribute("userId").toString()));
    }

}
