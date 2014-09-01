package com.langyi.weixin.controller.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.langyi.weixin.model.User;
import com.langyi.weixin.service.UserService;
import com.weixin.WeiXin;
import com.weixin.vo.recv.WxRecvTextMsg;
import com.weixin.vo.send.WxSendMsg;
import com.weixin.vo.send.WxSendTextMsg;


public class GetUserCreditProcessor extends TextProcessor {
	
	private static Logger LOG = Logger.getLogger(GetUserCreditProcessor.class.getName());
	
	@Autowired
	UserService userService;

	@Override
	public WxSendMsg process(WxRecvTextMsg receiveMsg) {
		String weixinAccount = receiveMsg.getFromUser();
		User user = userService.getUserByWeixinAccount(weixinAccount);
		String msgContent = null;
		
		if(user != null){
			LOG.debug("User exists : weixinAccount [" + weixinAccount + "]");
			msgContent = "您的微信账号还未和您的逸卡绑定\n\r"
					+ "<a href='baidu.com'>点击此处绑定</a>";
		} else {
			LOG.debug("User not exist : weixinAccount [" + weixinAccount + "]");
			msgContent = "您的逸卡号：\n\r"
					+ "剩余积分：";
		}
		
		WxSendMsg sendMsg = WeiXin.builderSendByRecv(receiveMsg);
		sendMsg = new WxSendTextMsg(sendMsg, msgContent);
		
		return sendMsg;
	}


}
