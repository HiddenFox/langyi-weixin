package com.langyi.weixin.controller.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.langyi.weixin.model.Account;
import com.langyi.weixin.service.AccountService;
import com.weixin.WeiXin;
import com.weixin.vo.recv.WxRecvEventMsg;
import com.weixin.vo.send.WxSendMsg;
import com.weixin.vo.send.WxSendTextMsg;

@Component("getCreditProcessor")
public class GetCreditProcessor extends EventProcessor {
	
	private static Logger LOG = Logger.getLogger(GetCreditProcessor.class.getName());
	
	@Autowired
	AccountService accountService;

	@Override
	public WxSendMsg process(WxRecvEventMsg receiveMsg) {
		LOG.debug("GetUserCreditProcessor start");
		
		String weixinAccount = receiveMsg.getFromUser();
		Account account = accountService.getUserByWeixinAccount(weixinAccount);
		String msgContent = null;
		
		if(account == null){
			LOG.debug("Account not exists : weixinAccount [" + weixinAccount + "]");
			msgContent = "您的微信账号还未和逸卡绑定。\n请发送\"bd+逸卡卡号\"绑定至您的逸卡。";
		} else {
			LOG.debug("Account exist : weixinAccount [" + weixinAccount + "]");
			msgContent = "您的逸卡号：" + account.getCardNumber()
					+ "\n当前积分：" + account.getCredit()
					+ "\n发送jb可以解除绑定。";
		}
		
		WxSendMsg sendMsg = WeiXin.builderSendByRecv(receiveMsg);
		sendMsg = new WxSendTextMsg(sendMsg, msgContent);
		
		return sendMsg;
	}


}
