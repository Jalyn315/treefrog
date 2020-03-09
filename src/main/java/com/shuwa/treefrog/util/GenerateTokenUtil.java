package com.shuwa.treefrog.util;

import org.springframework.util.DigestUtils;

/**
 * 生成 token 字符串的工具类
 */
public class GenerateTokenUtil {
    /**
     * 密钥，不能泄露
     */
    private final static String SECRET_KEY = "SUMMERLV";

    /**
     * 通过 密钥+邮箱 然后通过 MD5 算法生成一串 token 字符
     *
     * @param email
     * @return
     */
    public static String getToken(String email) {
        String newStr = email + SECRET_KEY;

        /**
         * 使用 springboot 自带的 md5 加密算法
         */
        return DigestUtils.md5DigestAsHex(newStr.getBytes());

    }
}
