package cn.joe9607.service;

import cn.joe9607.pojo.User;

public interface UserService {

	boolean sendMsg(String countryCode, String phoneNum);

	boolean verify(String phoneNum, String verifyCode);

	void register(User user);


}
