package com.langyi.weixin.controller.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.langyi.weixin.model.Account;
import com.langyi.weixin.service.AccountService;
import com.langyi.weixin.service.util.Param;
import com.weixin.WeiXin;
import com.weixin.vo.recv.WxRecvTextMsg;
import com.weixin.vo.send.WxSendMsg;
import com.weixin.vo.send.WxSendTextMsg;

@Component("getUserCreditProcessor")
public class GetCreditProcessor extends TextProcessor {
	
	private static Logger LOG = Logger.getLogger(GetCreditProcessor.class.getName());
	
	@Autowired
	AccountService accountService;

	@Override
	public WxSendMsg process(WxRecvTextMsg receiveMsg) {
		LOG.debug("GetUserCreditProcessor start");
		
		String weixinAccount = receiveMsg.getFromUser();
		LOG.debug("1");
		Account account = accountService.getUserByWeixinAccount(weixinAccount);
		LOG.debug("2");
		String msgContent = null;
		
		if(account == null){
			LOG.debug("Account not exists : weixinAccount [" + weixinAccount + "]");
			msgContent = "您的微信账号还未和您的逸卡绑定\n\r"
					+ "<a href='" + Param.URL_LOGIN + "'>点击此处绑定</a>";
		} else {
			LOG.debug("Account exist : weixinAccount [" + weixinAccount + "]");
			msgContent = "您的逸卡号：\n\r"
					+ "剩余积分：" + account.getCredit();
		}
		
		WxSendMsg sendMsg = WeiXin.builderSendByRecv(receiveMsg);
		sendMsg = new WxSendTextMsg(sendMsg, msgContent);
		
		return sendMsg;
	}


}
