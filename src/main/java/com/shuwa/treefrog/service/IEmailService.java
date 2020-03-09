package com.shuwa.treefrog.service;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailService {
    /**
     * 传入 emial 发送密码重置邮件
     * 进行异步调用发送邮件接口
     *
     * @param emailMessage
     */
    void sendPasswordResetEmail(SimpleMailMessage emailMessage);

    /**
     * 检查邮箱是否已存在
     *
     * @param email
     * @return
     */
    boolean checkEmail(String email);

    /**
     * 往 redis 中插入一条以 email 为 key token 为值的新记录
     *
     * @param token
     * @return
     */
    boolean insertNewResetRecord(String email, String token, SimpleMailMessage emailMessage);


    /**
     * 设置key-value
     *
     * @param key
     * @param value
     */
    void setKey(String key, String value);

    /**
     * 获取key
     *
     * @param key
     * @return
     */
    String getValue(String key);
}
