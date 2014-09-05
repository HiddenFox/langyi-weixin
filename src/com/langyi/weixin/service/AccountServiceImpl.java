package com.langyi.weixin.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langyi.weixin.dao.AccountDao;
import com.langyi.weixin.model.Account;
import com.langyi.weixin.util.HttpRequest;
import com.langyi.weixin.util.Param;

@Service
public class AccountServiceImpl implements AccountService{
	
	private static Logger LOG = Logger.getLogger(AccountServiceImpl.class.getName());
	
	@Autowired
	private AccountDao accountDao;

	
	@Override
	public Account getUserByWeixinAccount(String weixinAccount) {
		Account account = accountDao.getByWeixinAccount(weixinAccount);
		if (account != null) {
			String credit = this.getCreditByCardNumber(account.getCardNumber());
			account.setCredit(credit);
		}
		return account;
	}

	@Override
	public boolean login(Account account) {
		String credit = this.getCreditByCardNumber(account.getCardNumber());
		if ("bcz".equals(credit)) {
			return false;
		} else {
			accountDao.insert(account);
			account.setCredit(credit);
			return true;
		}
	}
	
	private String getCreditByCardNumber(String cardNumber) {
		String credit = HttpRequest.sendGet(Param.URL_GET_CREDIT + cardNumber);
		LOG.debug("get credit:" + credit + ", cardNumber:" + cardNumber);
		return credit;
	}
	
	public boolean logout(Account account) {
		accountDao.deleteByWeixinAccount(account.getWeixinAccount());
		return true;
	}

}
