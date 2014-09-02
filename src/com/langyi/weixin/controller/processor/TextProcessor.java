package com.langyi.weixin.controller.processor;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.langyi.weixin.controller.WeixinController;
import com.weixin.vo.recv.WxRecvTextMsg;
import com.weixin.vo.send.WxSendMsg;

@Component("textProcessor")
public class TextProcessor implements MessageProcessor<WxRecvTextMsg> {
	
	private static Logger LOG = Logger.getLogger(WeixinController.class.getName());

	@Resource(name="getUserCreditProcessor")
	private TextProcessor getUserCreditProcessor;
	
	@Override
	public WxSendMsg process(WxRecvTextMsg receiveMsg) {
		String command = receiveMsg.getContent().toLowerCase();
		LOG.debug("Receive command: " + command);
		if ("jf".equals(command)) {
			return getUserCreditProcessor.process(receiveMsg);
		}
		return null;
	}

}
