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

//	@Resource(name="getUserCreditProcessor")
//	private TextProcessor getUserCreditProcessor;
	
	@Resource(name="bindCardProcessor")
	private TextProcessor bindCardProcessor;
	
	@Resource(name="unbindCardProcessor")
	private TextProcessor unbindCardProcessor;
	
	@Override
	public WxSendMsg process(WxRecvTextMsg receiveMsg) {
		String command = receiveMsg.getContent().toLowerCase();
		LOG.debug("Receive command: " + command);
//		if (command.indexOf("jf") == 0) {
//			return getUserCreditProcessor.process(receiveMsg);
//		}
		if (command.indexOf("bd") == 0) {
			return bindCardProcessor.process(receiveMsg);
		}
		if (command.indexOf("jb") == 0) {
			return unbindCardProcessor.process(receiveMsg);
		}
		return null;
	}

}
