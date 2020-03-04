package com.shuwa.treefrog.util.slidingcodeutils;

import com.shuwa.treefrog.config.GeetestConfig;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class GeeTestUtil {
    /**
     * 验证结果
     */
    public final static Integer RESULT_FAIL = 0;
    public final static Integer RESULT_OK = 1;
    /**
     * 极验服务器状态
     */
    public final static Integer GT_SERVER_STATUS_CODE_OK = 1;

    /**
     * @param httpSession
     * @param challenge
     * @param validate
     * @param seccode
     * @return
     */
    public static boolean validate(HttpSession httpSession, String challenge, String validate, String seccode) {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) httpSession.getAttribute(gtSdk.gtServerStatusSessionKey);
        //从session中获取userid
        String userid = (String) httpSession.getAttribute("userid");
        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP
        int gtResult = RESULT_FAIL;
        gtSdk.debugCode = false; //关闭调试
        if (gt_server_status_code == GT_SERVER_STATUS_CODE_OK) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
//            System.out.println(gtResult);
        }
        return gtResult == RESULT_OK;
    }
}
