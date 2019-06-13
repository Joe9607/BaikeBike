package cn.joe9607.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import cn.joe9607.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public boolean sendMsg(String countryCode, String phoneNum) {
		boolean flag = true;
		
		
		// 调用腾讯云的短信API
		int appid = Integer.parseInt(stringRedisTemplate.opsForValue().get("appid"));
		String appkey = stringRedisTemplate.opsForValue().get("appkey");		
		
		//生成一个随机的4位数字
		String code = (int)((Math.random() * 9 + 1) * 1000) + "";
		
		SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		
		try {
			//向对应手机号码的用户发送短信
			 SmsSingleSenderResult result = ssender.send(0, countryCode, phoneNum, "您的登录验证码为" + code +"。", "", "");
			//将发送的手机号作为key，验证码作为value保存到Redis中
			 System.out.println(result);
			stringRedisTemplate.opsForValue().set(phoneNum,code,300,TimeUnit.SECONDS);
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} 
		
		
		return flag;
	}

	@Override
	public boolean verify(String phoneNum, String verifyCode) {
		boolean flag = false;
		// 调用RedisTemplate，根据手机号的key查找对应的验证码
		String code = stringRedisTemplate.opsForValue().get(phoneNum);
		if(code != null && code.equals(verifyCode)) {
			flag = true;
		}
		return flag;
	}

	@Override
	public void register(User user) {
		
		//调用mongodb的dao，将用户数据保存起来
		mongoTemplate.insert(user);
		
	}

}
