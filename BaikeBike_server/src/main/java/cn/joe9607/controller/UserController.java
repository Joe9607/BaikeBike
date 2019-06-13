package cn.joe9607.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.joe9607.pojo.User;
import cn.joe9607.service.UserService;


@Controller
public class UserController {
	
	@Autowired
	private UserService userServicce;
	
	@RequestMapping("user/genCode")
	@ResponseBody
	public boolean genVerifyCode(String countryCode,String phoneNum) { 
		boolean flag = userServicce.sendMsg(countryCode,phoneNum);
		return flag;
	}
	
	
	@RequestMapping("/user/verify")
	@ResponseBody
	public boolean verify(String phoneNum,String verifyCode) {
		//调用service层，进行校验
		return userServicce.verify(phoneNum,verifyCode);
	}
	
	
	@RequestMapping("/user/register")
	@ResponseBody
	public boolean reg(@RequestBody User user) {//接收json类型的参数，然后set到对应的实体类中的属性
		boolean flag = true;
		//调用service，将用户的信息保存
		try {
			userServicce.register(user);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
		
		
	}

}
