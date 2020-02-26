package com.shuwa.treefrog.exception;

/**
 * 注册 异常
 */
public class RegisterException extends Exception {
    //无参构造方法
    public RegisterException(){
        super();
    }

    //有参的构造方法
    public RegisterException(String message){
        super(message);
    }
}
