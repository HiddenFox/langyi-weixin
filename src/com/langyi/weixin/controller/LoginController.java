package com.langyi.weixin.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.langyi.weixin.model.Account;
import com.langyi.weixin.service.AccountService;

@Controller
public class LoginController {
	
	private static Logger LOG = Logger.getLogger(LoginController.class.getName());
	
	@Autowired
	AccountService accountService;

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginForm(@RequestParam(required=true) String weixinAccount, Model model) {
		Account account = accountService.getUserByWeixinAccount(weixinAccount);
		if(account != null){
			LOG.debug("Account exists : weixinAccount [" + weixinAccount + "]");
			model.addAttribute("account", account);
			return "success";
		} else {
			LOG.debug("Account does not exist : weixinAccount [" + weixinAccount + "]");
			account = new Account();
			account.setWeixinAccount(weixinAccount);
			model.addAttribute("account", account);
			return "login";
		}
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String submitLoginForm(@ModelAttribute("account") Account account, Model model) throws IOException {
		if (accountService.login(account)){
			model.addAttribute("account", account);
			return "success";
		} else {
			model.addAttribute("error", "卡号不存在，绑定失败！");
			model.addAttribute("account", account);
			return "login";
		}
	}

}
