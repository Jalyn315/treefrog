package com.shuwa.treefrog.exception;

public class TypeNameException extends Exception {
    //无参构造方法
    public TypeNameException() {
        super();
    }

    //有参的构造方法
    public TypeNameException(String message) {
        super(message);
    }
}
