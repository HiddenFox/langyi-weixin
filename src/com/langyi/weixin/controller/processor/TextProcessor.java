package com.langyi.weixin.controller.processor;

import org.apache.log4j.Logger;

import com.langyi.weixin.controller.WeixinController;
import com.weixin.vo.recv.WxRecvTextMsg;
import com.weixin.vo.send.WxSendMsg;

public class TextProcessor implements MessageProcessor<WxRecvTextMsg> {
	
	private static Logger LOG = Logger.getLogger(WeixinController.class.getName());

	@Override
	public WxSendMsg process(WxRecvTextMsg receiveMsg) {
		String command = receiveMsg.getContent().toLowerCase();
		LOG.debug("Receive command: " + command);
		if ("jf".equals(command)) {
			return new GetUserCreditProcessor().process(receiveMsg);
		}
		return null;
	}

}
