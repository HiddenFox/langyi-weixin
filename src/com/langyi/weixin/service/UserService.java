package com.langyi.weixin.service;

import com.langyi.weixin.model.User;

public interface UserService {
	
	public boolean doesUserExist(String weixinAccount);
	
	public User getUserByWeixinAccount(String weixinAccount);

}
