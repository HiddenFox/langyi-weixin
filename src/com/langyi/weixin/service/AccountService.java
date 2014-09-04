package com.langyi.weixin.service;

import com.langyi.weixin.model.Account;

public interface AccountService {
	
	public Account getUserByWeixinAccount(String weixinAccount);
	
	public boolean login(Account user);

}
