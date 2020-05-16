package com.shuwa.treefrog.exception;

/**
 * 自定义 登录 异常
 */
public class LoginException extends Exception {
    //无参构造方法
    public LoginException() {
        super();
    }

    //有参的构造方法
    public LoginException(String message) {
        super(message);
    }
}
