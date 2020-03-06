package com.shuwa.treefrog.service.impl;

import com.shuwa.treefrog.service.ISmsService;
import com.shuwa.treefrog.util.sendSMS.SmsSingleSender;
import com.shuwa.treefrog.util.sendSMS.SmsSingleSenderResult;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class SmsService implements ISmsService {


    @Override
    public boolean sendSms(String mobile) {
        if (!StringUtils.isEmpty(getValue(mobile))) {
            System.out.println("if");
            return false;
        } else {
            System.out.println("else");
            String verifiCode = getVerifCode();
            setKey(mobile, verifiCode);
            //请根据实际 accesskey 和 secretkey，保密，不能外传
            String accesskey = "";
            String secretkey = "";
            //初始化单发
            SmsSingleSender singleSender = null;
            try {
                singleSender = new SmsSingleSender(accesskey, secretkey);
                //普通单发,注意前面必须为【】符号包含，置于头或者尾部。
                SmsSingleSenderResult singleSenderResult;
                String msg = "【summer】树蛙科技提醒您：您的验证码：" + verifiCode + "，一分钟之内有效，请尽快完成验证，请勿泄漏。";
                /*singleSenderResult = */
                singleSender.send(0, "86", mobile, msg, "", "");
//                System.out.println(singleSenderResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } //end if...else
        return true;
    }

    @Override
    public boolean checkIsCorrectCode(String mobile, String code) {
        return false;
    }

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void setKey(String key, String value) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, 60, TimeUnit.SECONDS);//1分钟过期
    }

    @Override
    public String getValue(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    /**
     * 生成六位数验证码
     *
     * @return
     */
    protected String getVerifCode() {
        // 100000~999999
        int max = 999999;
        int min = 100000;
        int verifCode = min + (int) (Math.random() * (max - min + 1));
        return verifCode + "";
    }// end getVerifCode()
}
