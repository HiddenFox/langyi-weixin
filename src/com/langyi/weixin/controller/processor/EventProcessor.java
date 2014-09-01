package com.langyi.weixin.controller.processor;

import com.weixin.vo.recv.WxRecvEventMsg;
import com.weixin.vo.send.WxSendMsg;

public class EventProcessor implements MessageProcessor<WxRecvEventMsg>{

	@Override
	public WxSendMsg process(WxRecvEventMsg receiveMsg) {
	    String event = receiveMsg.getEvent();
	    
		if ("subscribe".equals(event)) {
			return new WelcomeProcessor().process(receiveMsg);
		}
	    
		return null;
	}

	
}
