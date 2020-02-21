package com.shuwa.treefrog.util.sendSMS;

import org.springframework.stereotype.Component;

@Component
public class SmsSDKDemo {
    
    public boolean sendSms(String phoneNumber, int verifCode) {
    	boolean flag = false;
    	try {
    		//请根据实际 accesskey 和 secretkey，保密，不能外传
    		String accesskey = "5d7dd33887b65f1f37d69075";
    		String secretkey ="3c8554aed05749709b7020c7ba34b9cf";
    		 //初始化单发
	    	SmsSingleSender singleSender = new SmsSingleSender(accesskey, secretkey);
	    	 //普通单发,注意前面必须为【】符号包含，置于头或者尾部。
	    	SmsSingleSenderResult singleSenderResult;
	    	singleSenderResult = singleSender.send(0, "86", phoneNumber, "【summer】尊敬的用户：树蛙科技提醒您：您的验证码："+verifCode+"，工作人员不会索取，请勿泄漏。", "", "");
	    	System.out.println(singleSenderResult);
	    	
	    	//语音验证码发送
    		//SmsVoiceVerifyCodeSender smsVoiceVerifyCodeSender = new SmsVoiceVerifyCodeSender(accesskey,secretkey);
    		//SmsVoiceVerifyCodeSenderResult smsVoiceVerifyCodeSenderResult = smsVoiceVerifyCodeSender.send("86",phoneNumber, "444144",2,"");
    		//System.out.println(smsVoiceVerifyCodeSenderResult);
	    	flag = true;
    	} catch (Exception e) {
			e.printStackTrace();
		}//try...catch
    	
    	return flag;
	}//end sendSms()
}
