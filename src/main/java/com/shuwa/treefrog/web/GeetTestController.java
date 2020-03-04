package com.shuwa.treefrog.web;

import com.shuwa.treefrog.config.GeetestConfig;
import com.shuwa.treefrog.util.slidingcodeutils.GeetestLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 极验验证相关
 */
@RequestMapping("geetTest")
@Controller
public class GeetTestController {
    /**
     * 注入session
     */
    @Autowired
    private HttpSession httpSession;

    /**
     * 验证1初始化
     *
     * @return
     */
    @ResponseBody
    @GetMapping("register1")
    public String register1() {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String resStr = "{}";
        String userid = "test";
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);
        //将服务器状态设置到session中
        httpSession.setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //将userid设置到session中
        httpSession.setAttribute("userid", userid);
        resStr = gtSdk.getResponseStr();
        return resStr;
    }
}
