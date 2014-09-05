package com.langyi.weixin.controller.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.langyi.weixin.model.Account;
import com.langyi.weixin.service.AccountService;
import com.weixin.WeiXin;
import com.weixin.vo.recv.WxRecvTextMsg;
import com.weixin.vo.send.WxSendMsg;
import com.weixin.vo.send.WxSendTextMsg;

@Component("unbindCardProcessor")
public class UnbindCardProcessor extends TextProcessor {
	private static Logger LOG = Logger.getLogger(UnbindCardProcessor.class.getName());
	
	@Autowired
	AccountService accountService;

	@Override
	public WxSendMsg process(WxRecvTextMsg receiveMsg) {
		LOG.debug("UnbindCardProcessor start");
		
		Account account = new Account();
		account.setWeixinAccount(receiveMsg.getFromUser());
		
		String msgContent = null;
		
		if(accountService.logout(account)){
			LOG.debug("Account exist : weixinAccount [" + account.getWeixinAccount() + "]");
			msgContent = "已解除绑定。";
		} else {
			LOG.debug("Account not exists : weixinAccount [" + account.getWeixinAccount() + "]");
			msgContent = "解除绑定失败，请联系管理员。";
		}
		
		WxSendMsg sendMsg = WeiXin.builderSendByRecv(receiveMsg);
		sendMsg = new WxSendTextMsg(sendMsg, msgContent);
		
		return sendMsg;
	}
}
