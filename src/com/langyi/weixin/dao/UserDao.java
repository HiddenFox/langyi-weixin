package com.langyi.weixin.dao;

import org.springframework.stereotype.Repository;

import com.langyi.weixin.model.User;


@Repository
public interface UserDao {

	public User selectByWeixinAccount(String weixinAccount);
	
	public User selectByUserIdAndCardNumber(User user);
	
}
