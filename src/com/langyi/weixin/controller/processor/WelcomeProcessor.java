package com.langyi.weixin.controller.processor;

import com.weixin.WeiXin;
import com.weixin.vo.recv.WxRecvEventMsg;
import com.weixin.vo.send.WxSendMsg;
import com.weixin.vo.send.WxSendTextMsg;

public class WelcomeProcessor extends EventProcessor{
	
	@Override
	public WxSendMsg process(WxRecvEventMsg receiveMsg) {
		String content = "欢迎关注逸卡之家！";
		WxSendMsg sendMsg = WeiXin.builderSendByRecv(receiveMsg);
        return new WxSendTextMsg(sendMsg, content);
	}

}
