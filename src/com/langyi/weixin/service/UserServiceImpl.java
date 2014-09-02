package com.langyi.weixin.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langyi.weixin.dao.UserDao;
import com.langyi.weixin.model.User;

@Service
public class UserServiceImpl implements UserService{
	
	private static Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean doesUserExist(String weixinAccount) {
		User user = userDao.selectByWeixinAccount(weixinAccount);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}
	
	@Override
	public User getUserByWeixinAccount(String weixinAccount) {
		LOG.debug("getUserByWeixinAccount " + weixinAccount);
		return userDao.selectByWeixinAccount(weixinAccount);
	}

	@Override
	public boolean login(User user) {
		if (userDao.selectByUserIdAndCardNumber(user) != null) {
			return true;
		} else {
			return false;
		}
	}

}
