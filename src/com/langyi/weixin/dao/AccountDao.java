package com.langyi.weixin.dao;

import org.springframework.stereotype.Repository;

import com.langyi.weixin.model.Account;


@Repository
public interface AccountDao {
	
	public int insert(Account account);

	public Account getByWeixinAccount(String weixinAccount);
	
	public int deleteByWeixinAccount(String weixinAccount);
	
}
