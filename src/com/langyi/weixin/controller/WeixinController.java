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

import com.weixin.WeiXin;
import com.weixin.vo.recv.WxRecvEventMsg;
import com.weixin.vo.recv.WxRecvMsg;
import com.weixin.vo.recv.WxRecvTextMsg;
import com.weixin.vo.send.WxSendMsg;
import com.weixin.vo.send.WxSendNewsMsg;
import com.weixin.vo.send.WxSendTextMsg;

@Controller
@RequestMapping(value="/weixin")
public class WeixinController {
	
	private static Logger LOG = Logger.getLogger(WeixinController.class.getName());
	private final String TOKEN = "TMIA";
	
	private final String URL_DOMAIN 	= "http://tminvoice.duapp.com/";
	private final String URL_POLICY 	= URL_DOMAIN + "policy";
	private final String URL_CONTACT 	= URL_DOMAIN + "contact";
	private final String URL_LOGIN 		= URL_DOMAIN + "login?weixinAccount=";
	private final String URL_LIST 		= URL_DOMAIN + "list";
	private final String URL_INVOICE 	= URL_DOMAIN + "invoice?weixinAccount=";
	private final String URL_MY_LIST 	= URL_DOMAIN + "my?weixinAccount=";
	private final String URL_POLICY_IMG = URL_DOMAIN + "img/policy.jpg";

	
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
		
		WxSendMsg sendMsg = WeiXin.builderSendByRecv(receiveMsg);
		
		LOG.debug("get a message, message id: " + receiveMsg.getMsgId() + 
				", message type：" + receiveMsg.getMsgType() + 
				", from user：" + receiveMsg.getFromUser() + 
				", to user：" + receiveMsg.getToUser() + 
				", create time：" + receiveMsg.getCreateDt());
		
		if(receiveMsg instanceof WxRecvEventMsg) {
		    WxRecvEventMsg m = (WxRecvEventMsg) receiveMsg;
		    String event = m.getEvent();
		    
		    if("subscribe".equals(event)) {
		        String content = "欢迎关注TMIA（TrendMicro Invoice Assistant）\r\n\r\n" +
		        		"旅游归来发现发票不够怎么办？\r\n" +
		        		"吃完大餐发现忘记了公司的发票抬头怎么办？\r\n" +
		        		"有了多余的发票不知道给谁怎么办？\r\n" +
		        		"TMIA帮您解决一切与发票相关的问题！\r\n\r\n" +
		        		"请回复任意内容调出菜单";
		        sendMsg = new WxSendTextMsg(sendMsg, content);
		    }
		} else if (receiveMsg instanceof WxRecvTextMsg){
//			String content = "message id : " + receiveMsg.getMsgId() + 
//					"\r\n message type：" + receiveMsg.getMsgType() + 
//					"\r\n from user：" + receiveMsg.getFromUser() + 
//					"\r\n to user：" + receiveMsg.getToUser() + 
//					"\r\n create time：" + receiveMsg.getCreateDt() + 
//					"\r\n content：" + ((WxRecvTextMsg)receiveMsg).getContent() +
//					"\r\n\r\n<a href=\"http://tminvoice.duapp.com/static/hello.html\">点击进入 </a>";
//			sendMsg = new WxSendTextMsg(sendMsg, content);
			
			sendMsg = new WxSendNewsMsg(sendMsg)
			.addItem("公司相关Policy", null, URL_POLICY_IMG, URL_POLICY)
			.addItem("身份认证", null, null, URL_LOGIN + receiveMsg.getFromUser())
			.addItem("大家的发票", null, null, URL_LIST)
			.addItem("我要共享发票", null, null, URL_INVOICE + receiveMsg.getFromUser())
			.addItem("管理我的发票", null, null, URL_MY_LIST + receiveMsg.getFromUser())
			.addItem("联系管理员", null, null, URL_CONTACT);
		
		} else {
			return null;
		}

		// 多图消息
		//WxSendNewsMsg newsMsg = new WxSendNewsMsg(sendMsg)
		                //.addItem("标题", "描述", "图片地址", "点击后跳转的链接");
		                //.addItem....
		                //最多可以添加10个
		// 音乐消息
		//new WxSendMusicMsg(sendMsg, "标题","描述","低品质音乐地址", "高品质音乐地址 (wifi环境会使用这个地址进行播放)");

		try {
			WeiXin.send(sendMsg, response.getOutputStream());
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return null;
	}

}
