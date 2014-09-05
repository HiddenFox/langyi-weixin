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

@Component("bindCardProcessor")
public class BindCardProcessor extends TextProcessor {
	
	private static Logger LOG = Logger.getLogger(BindCardProcessor.class.getName());
	
	@Autowired
	AccountService accountService;

	@Override
	public WxSendMsg process(WxRecvTextMsg receiveMsg) {
		LOG.debug("BindCardProcessor start");
		
		Account account = new Account();
		account.setWeixinAccount(receiveMsg.getFromUser());
		account.setCardNumber(receiveMsg.getContent().substring(2));
		
		String msgContent = null;
		
		if(account.getCardNumber().matches("\\d+") 
				&& account.getCardNumber().length() < 20 
				&& accountService.login(account)){
			LOG.debug("Account exist : weixinAccount [" + account.getWeixinAccount() + "]");
			msgContent = "已成功绑定逸卡：" + account.getCardNumber() 
					+ "\n当前积分：" + account.getCredit()
					+ "\n发送jb可以解除绑定。";
					
		} else {
			LOG.debug("Account not exists : weixinAccount [" + account.getWeixinAccount() + "]");
			msgContent = "卡号不正确，绑定失败。";
		}
		
		WxSendMsg sendMsg = WeiXin.builderSendByRecv(receiveMsg);
		sendMsg = new WxSendTextMsg(sendMsg, msgContent);
		
		return sendMsg;
	}
}
