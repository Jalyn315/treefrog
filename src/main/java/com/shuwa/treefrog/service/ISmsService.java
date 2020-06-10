package com.shuwa.treefrog.service;

/**
 * 发送短信接口服务
 */
public interface ISmsService {
    /**
     * @param mobile
     * @return
     */
    String sendSms(String mobile);

    /**
     * 判断验证码是否正确
     *
     * @param mobile
     * @param code
     * @return
     */
    boolean checkIsCorrectCode(String mobile, String code);

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
