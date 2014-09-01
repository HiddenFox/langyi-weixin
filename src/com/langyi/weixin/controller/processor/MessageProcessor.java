package com.langyi.weixin.controller.processor;

import com.weixin.vo.recv.WxRecvMsg;
import com.weixin.vo.send.WxSendMsg;

public interface MessageProcessor<T extends WxRecvMsg> {
	public WxSendMsg process(T receiveMsg);
}
