package com.weixin.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.weixin.vo.recv.WxRecvMsg;
import com.weixin.vo.recv.WxRecvTextMsg;

public class WxRecvTextMsgParser extends WxRecvMsgBaseParser{

	@Override
	protected WxRecvTextMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvTextMsg(msg, getElementText(root, "Content"));
	}

	
}
