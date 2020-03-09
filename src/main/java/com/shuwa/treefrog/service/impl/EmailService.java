package com.shuwa.treefrog.service.impl;

import com.shuwa.treefrog.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService implements IEmailService {
    /**
     * redis 存储数据的 api
     */
    @Resource
    private RedisTemplate redisTemplate;
    /**
     * 发送邮件的 api
     */
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendPasswordResetEmail(SimpleMailMessage emailMessage) {
        javaMailSender.send(emailMessage);
    }

    @Override
    public boolean checkEmail(String email) {
        //插入 email-token 键值对前需要查看 redis 数据库中是否已有记录
        //有记录，需要判断是否达到每日发送邮件上限次数
        if (!StringUtils.isEmpty(getValue(email))) {
            System.out.println("if");
            return false;
        }
        return true;
    }

    @Override
    public boolean insertNewResetRecord(String email, String token, SimpleMailMessage emailMessage) {
        sendPasswordResetEmail(emailMessage);
        setKey(email, token);
        return true;
    }

    @Override
    public void setKey(String key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, 300, TimeUnit.SECONDS);//5min 过期
    }

    @Override
    public String getValue(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }
}
