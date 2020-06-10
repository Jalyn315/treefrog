package com.shuwa.treefrog.web;

import com.shuwa.treefrog.constant.UserConstant;
import com.shuwa.treefrog.service.impl.EmailService;
import com.shuwa.treefrog.service.impl.UserService;
import com.shuwa.treefrog.util.GenerateTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Value("${spring.mail.username}")
    private String from;

//    @RequestMapping(value = "/login")
//    public String toLoginPage() {
//        return "login";
//    }

    /**
     * 传递到 填写邮箱 页面
     *
     * @return
     */
    @RequestMapping(value = "/forgetPassword")
    public String toSendPage() {
        return "fill_email";
    }

    /**
     * 根据 email 发送重置密码邮件
     *
     * @param email
     */
    @PostMapping(value = "/sendEmail")
    public String sendEmail(@RequestParam("email") String email
            , Model model
            , HttpServletRequest request) {
        System.out.println("EmailController->sendEmail");
        //验证邮箱是否为 "" || null
        if (email == null || "".equals(email)) {
            model.addAttribute("emaillError", "邮箱为空！请正确填写邮箱");
            //返回 fill_email.html
            return "fill_email";
        } else if (!email.matches(UserConstant.emailRegex)) { //!email.matches(UserConstant.emailRegex) !"lv.summer@qq.com".equals(email)
            model.addAttribute("emaillError", "邮箱格式不正确！");
            //返回 fill_email.html
            return "fill_email";
        } else if (!email.equals(userService.getEmail(email))) {
            //验证邮箱是否已经被注册，被注册的邮箱才能去发送邮件
            model.addAttribute("emaillError", "该邮箱未被注册！");
            //返回 fill_email.html
            return "fill_email";
        } else {
            if (!emailService.checkEmail(email)) {
                //邮箱已经发送了重置密码邮件
                model.addAttribute("emaillError", "已经发送邮件，请查验，5分钟后可以再发送！");
                return "fill_email";
            } else {
                String token = GenerateTokenUtil.getToken(email);
                // 设置邮件内容
                String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
                SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
                passwordResetEmail.setFrom(from);
                passwordResetEmail.setTo(email);
                passwordResetEmail.setSubject("【树蛙科技】忘记密码-重置");
                passwordResetEmail.setText("您正在申请重置密码，请点击此链接重置密码: \n" + appUrl + "/toResetPasswordPage?email=" + email + "&token=" + token);
                //调用 EmailService 层的 insertNewResetRecord() 方法
                emailService.insertNewResetRecord(email, token, passwordResetEmail);
                //返回到重置密码页面 reset_password.html
                return "login";
            }
        }
    }


    /**
     * @return
     */
    @GetMapping(value = "/toResetPasswordPage")
    public String toResetPasswordPage(
            @RequestParam("email") String email
            , HttpSession session) {
        System.out.println("EmailController->toResetPasswordPage");
        session.setAttribute("email", email);
        session.setAttribute("token", GenerateTokenUtil.getToken(email));
        return "reset_password";
    }

    /**
     * @param password   重置的密码
     * @param rePassword 确认重置的密码
     */
    @PostMapping(value = "/userResetPassword")
    public String userResetPassword(
            @RequestParam("password") String password
            , @RequestParam("rePassword") String rePassword
            , Model model
            , HttpSession session) {
        //验证 password 是否为 null 和 符合密码的正则表达式，验证 repassword 是否和 password 相同
        if (password == null || "".equals(password)) {
            model.addAttribute("passwordError", "密码填入为空！");
        } else if (!password.matches(UserConstant.passwordRegex)) {
            model.addAttribute("passwordError", "密码格式不正确！必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间");
        } else if (!password.equals(rePassword)) {
            model.addAttribute("passwordError", "两次密码输入不相同！请重新输入");
        } else {
            String email = (String) session.getAttribute("email");
            String token = (String) session.getAttribute("token");
            //判断 token 是否失效（查看 redis 中是否有 token）
            if ((token != null && email != null) && token.equals(emailService.getValue(email))) {
                //调用 EmailService 层根据 email 重置密码方法
                userService.resetPasswordByEmail(password, email);
                return "login";
            }
        }
        return "reset_password";
    }
}
