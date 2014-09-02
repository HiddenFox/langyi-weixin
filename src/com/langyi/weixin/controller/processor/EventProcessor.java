package com.langyi.weixin.controller.processor;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.weixin.vo.recv.WxRecvEventMsg;
import com.weixin.vo.send.WxSendMsg;

@Component("eventProcessor")
public class EventProcessor implements MessageProcessor<WxRecvEventMsg>{

	@Resource(name="welcomeProcessor")
	private EventProcessor welcomeProcessor;
	
	@Override
	public WxSendMsg process(WxRecvEventMsg receiveMsg) {
	    String event = receiveMsg.getEventKey();
	    
		if ("subscribe".equals(event)) {
			return welcomeProcessor.process(receiveMsg);
		}
	    
		return null;
	}

	
}
