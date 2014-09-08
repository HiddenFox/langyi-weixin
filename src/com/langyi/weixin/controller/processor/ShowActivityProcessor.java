package com.langyi.weixin.controller.processor;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.langyi.weixin.service.AccountService;
import com.weixin.WeiXin;
import com.weixin.vo.recv.WxRecvEventMsg;
import com.weixin.vo.send.WxSendMsg;
import com.weixin.vo.send.WxSendTextMsg;

@Component("showActivityProcessor")
public class ShowActivityProcessor extends EventProcessor {
	
	private static Logger LOG = Logger.getLogger(ShowActivityProcessor.class.getName());
	
	@Autowired
	AccountService accountService;

	@Override
	public WxSendMsg process(WxRecvEventMsg receiveMsg) {
		LOG.debug("ShowActivityProcessor start");
		
		String msgContent = "逸卡百店联盟、打折、积分返话费服务功能现已隆重启动！"
				+ "关注微信-朗逸之家并且在朗逸之家微信平台回复卡号后7位，可获赠760积分等同38元。"
				+ "受会员优惠，还送移动、联通话费！更多会员惊喜请登录朗逸网站 "
				+ "<a href=\"http://www.langyi118.com\">http://www.langyi118.com</a>";
		WxSendMsg sendMsg = WeiXin.builderSendByRecv(receiveMsg);
		sendMsg = new WxSendTextMsg(sendMsg, msgContent);
		
		return sendMsg;
	}


}
