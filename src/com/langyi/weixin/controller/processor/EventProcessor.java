package com.langyi.weixin.controller.processor;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.weixin.vo.recv.WxRecvEventMsg;
import com.weixin.vo.send.WxSendMsg;

@Component("eventProcessor")
public class EventProcessor implements MessageProcessor<WxRecvEventMsg>{
	
	private static Logger LOG = Logger.getLogger(EventProcessor.class.getName());

	@Resource(name="welcomeProcessor")
	private EventProcessor welcomeProcessor;
	
	@Resource(name="getCreditProcessor")
	private EventProcessor getCreditProcessor;
	
	@Resource(name="showActivityProcessor")
	private EventProcessor showActivityProcessor;
	
	@Override
	public WxSendMsg process(WxRecvEventMsg receiveMsg) {
	    String event = receiveMsg.getEvent();
	    String eventKey = receiveMsg.getEventKey();
	    
	    LOG.debug("event:" + event + ", eventkey:" + eventKey );
	    
		if ("subscribe".equals(event)) {
			return welcomeProcessor.process(receiveMsg);
		}
		if ("CLICK".equals(event) && "MENU_CREDIT".equals(eventKey)) {
			return getCreditProcessor.process(receiveMsg);
		}
		if ("CLICK".equals(event) && "MENU_ACTIVITY".equals(eventKey)) {
			return showActivityProcessor.process(receiveMsg);
		}
	    
		return null;
	}

	
}
