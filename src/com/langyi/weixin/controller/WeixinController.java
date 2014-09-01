package com.langyi.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.langyi.weixin.controller.processor.EventProcessor;
import com.langyi.weixin.controller.processor.TextProcessor;
import com.weixin.WeiXin;
import com.weixin.vo.recv.WxRecvEventMsg;
import com.weixin.vo.recv.WxRecvMsg;
import com.weixin.vo.recv.WxRecvTextMsg;
import com.weixin.vo.send.WxSendMsg;

@Controller
@RequestMapping(value="/weixin")
public class WeixinController {
	
	private static Logger LOG = Logger.getLogger(WeixinController.class.getName());
	private final String TOKEN = "La1ng1Yi8";
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView authenticate(HttpServletResponse response, 
			@RequestParam(required=true) String signature, @RequestParam(required=true) String timestamp,
			@RequestParam(required=true) String nonce, @RequestParam(required=true) String echostr) throws Exception {
		
		LOG.debug("[Weixin authentication] timestamp:" + timestamp + ", nonce:" + nonce + 
				", echostr:" + echostr + ", signature:" + signature);
		
	    if(WeiXin.access(TOKEN, signature, timestamp, nonce)) {
	    	LOG.info("[Weixin authentication] Success");
	    	response.getWriter().write(echostr);
	    	return null;
	    }
		    
		LOG.info("[Weixin authentication] Fail");
		return null;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView handlePost(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		WxRecvMsg receiveMsg = null;
		try {
			receiveMsg = WeiXin.recv(request.getInputStream());
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		LOG.debug("get a message, message id: " + receiveMsg.getMsgId() + 
				", message type：" + receiveMsg.getMsgType() + 
				", from user：" + receiveMsg.getFromUser() + 
				", to user：" + receiveMsg.getToUser() + 
				", create time：" + receiveMsg.getCreateDt());
		
		WxSendMsg sendMsg = null;
		
		if(receiveMsg instanceof WxRecvEventMsg) {
			sendMsg = new EventProcessor().process((WxRecvEventMsg)receiveMsg);
		} else if (receiveMsg instanceof WxRecvTextMsg) {
			sendMsg = new TextProcessor().process((WxRecvTextMsg)receiveMsg);
		} else {
			return null;
		}

		if (sendMsg != null) {
			try {
				WeiXin.send(sendMsg, response.getOutputStream());
			} catch (JDOMException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
