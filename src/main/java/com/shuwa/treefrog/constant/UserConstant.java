package com.shuwa.treefrog.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 提供用户注册和登录的一些常量
 */
public class UserConstant {
    /**
     * 用于邮箱验证的正则表达式
     */
    public static final String emailRegex = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
    /**
     * 这个正则表示的是4-16位字母,数字,汉字,下划线 其中两个汉字是可以注册成功的,表示4个字符
     */
    public static final String userNameRegex =
            "/^([\\u4e00-\\u9fa5]{2,4})|([A-Za-z0-9_]{4,16})|([a-zA-Z0-9_\\u4e00-\\u9fa5]{3,16})$/;";

    /**
     * 强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)
     */
    public static final String passwordRegex =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,10}$";

    /**
     * 手机号码
     */
    public static final String phoneRegex =
            "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    /**
     * 用户名格式是否匹配正确
     */
    public static boolean isUserNameMatch = false;
    /**
     * 用户密码格式是否匹配正确
     */
    public static boolean isPasswordMatch = false;
    /**
     * 用户两次密码是否相同
     */
    public static boolean isPasswordIsEquals = false;
    /**
     * 用户手机号码是否匹配正确
     */
    public static boolean isPhoneMatch = false;
    /**
     * 用户验证码是否匹配正确
     */
    public static boolean isverificationCodeEquals = false;
    /**
     * 用户是否不存在
     */
    public static boolean isUserNotExists = false;
    /**
     * 手机号是否重复
     */
    public static boolean isPhoneDup = false;

    /**
     * 用于存储错误信息
     */
    public static Map<String, Object> errorMap = new HashMap<>();

    /**
     * 验证注册页面的字段
     *
     * @param userName
     * @param password
     * @param password2
     * @param phone
     * @param verifiCode
     * @return
     */
    public static boolean check(String userName, String password,
                                String password2, String phone,
                                String verifiCode, String verifiCode2) {
        //验证用户名
        if (userName != null && userName.matches(UserConstant.userNameRegex)) {
            UserConstant.isUserNameMatch = true;
        } else {
            UserConstant.isUserNameMatch = false;
            UserConstant.errorMap.put("userNameNotMatch", "用户名格式错误！请输入 (4-16位字母,数字,汉字,下划线)");
        }
        //验证密码
        if (password != null && password.matches(UserConstant.passwordRegex)) {
            UserConstant.isPasswordMatch = true;
        } else {
            UserConstant.isPasswordMatch = false;
            UserConstant.errorMap.put("passwordNotMatch", "密码格式错误！(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)");
        }

        //验证两次密码是否匹配
        if (password2 != null && password2.equals(password)) {
            UserConstant.isPasswordIsEquals = true;
        } else {
            UserConstant.isPasswordIsEquals = false;
            UserConstant.errorMap.put("passwordNotEquals", "两次密码不相同！");
        }

        //验证电话号码是否匹配
        if (phone != null && phone.matches(UserConstant.phoneRegex)) {
            UserConstant.isPhoneMatch = true;
        } else {
            UserConstant.isPhoneMatch = false;
            UserConstant.errorMap.put("phoneNotMatch", "手机号码格式不正确！");
        }

        //验证验证码是否匹配
        if (verifiCode != null && verifiCode.equals(verifiCode2)) {
            UserConstant.isverificationCodeEquals = true;
        } else {
            UserConstant.isverificationCodeEquals = false;
            UserConstant.errorMap.put("verifiCode", "验证码不正确！");
        }

        if (UserConstant.isPasswordIsEquals
                && UserConstant.isPasswordMatch
                && UserConstant.isPhoneMatch
                && UserConstant.isUserNameMatch
                && UserConstant.isverificationCodeEquals) {
            return true;
        }

        return false;
    } //end check()
}
