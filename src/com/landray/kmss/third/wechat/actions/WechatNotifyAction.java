package com.landray.kmss.third.wechat.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.third.wechat.dto.WeChatPostData;
import com.landray.kmss.third.wechat.forms.WeChatPostDataForm;
import com.landray.kmss.third.wechat.model.WechatMainConfig;
import com.landray.kmss.third.wechat.util.WeChatConfigUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

public class WechatNotifyAction extends BaseAction{
	private static MultiThreadedHttpConnectionManager connectionManager;
	
	private MultiThreadedHttpConnectionManager getConnectionManager() {
		if (connectionManager == null) {
			connectionManager = new MultiThreadedHttpConnectionManager();
		}
		return connectionManager;
	}

	
	public ActionForward toNotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){

		KmssMessages messages = new KmssMessages();
		try {
			WeChatPostDataForm weChatPostDataForm = (WeChatPostDataForm) form;
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("toNotify");
		}
	
	}

	public ActionForward doSendNotify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		KmssMessages messages = new KmssMessages();
		try {
			WeChatPostDataForm weChatPostDataForm = (WeChatPostDataForm) form;
			List<WeChatPostData> postDatas = new ArrayList<WeChatPostData>();
			
			WeChatPostData kkPostData = new WeChatPostData();
			kkPostData.setPersonId(weChatPostDataForm.getPersonId());
			kkPostData.setPostSubject(weChatPostDataForm.getPostSubject());
			
			postDatas.add(kkPostData);
			Map<String,String>retMap =executePostData(postDatas);
			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String result=JSONObject.fromObject(retMap).toString();
			// 记录日志信息
			if (UserOperHelper.allowLogOper("doSendNotify", "*")) {
				UserOperHelper.logMessage(result);
			}
			out.print(result);
			out.flush();
		} catch (Exception e) {
			messages.addError(e);
		}
		
		return null;
	}
	

	@SuppressWarnings("deprecation")
	public Map<String,String>  executePostData(List<WeChatPostData> postDatas) {
		Map<String,String> retMap = new HashMap<String,String>();
		String retInfor="";
		String code="";
		if (postDatas.isEmpty()) {
			return null;
		}
		HttpClient httpClient = new HttpClient(getConnectionManager());
		// 设置超时
		httpClient.setConnectionTimeout(15000);
		httpClient.setTimeout(15000);
		
		String scene="";
		if (scene == null || "".equals(scene)) {
			scene = WeChatConfigUtil.scene;
		}

		PostMethod post = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("scene", scene);
		map.put("data", postDatas);
		map.put("type", "forTest");
		JSONObject jo = JSONObject.fromObject(map);
		
		try {
			
			RequestEntity entity = new StringRequestEntity(jo.toString(), null,"utf-8");
			WechatMainConfig config = new WechatMainConfig();
			
			if (config != null) {
				String qyEnable = config.getLwechat_qyEnable();
				if (StringUtils.isNotEmpty(qyEnable) && "true".equals(qyEnable)) {
						String qyBaseUrl = config.getLwechat_qyUrl();
						String qyNotifyUrl = config.getLwechat_qyNotifyUrl();
						String qySendTaget = qyBaseUrl + qyNotifyUrl;
						
						if (StringUtils.isNotEmpty(qySendTaget)) {
							post = new PostMethod(qySendTaget);
							try {
								post.setRequestEntity(entity);
								int result = httpClient.executeMethod(post);
								if (result == 200) {
									code="200";
									String resString = post.getResponseBodyAsString();
									retInfor=resString;
								} else {
									code="500";
									String resString = post.getResponseBodyAsString();
									retInfor="推送到企业号消息推送结果:"+resString;
								}
							} catch (Exception e) {
								    code="500";
								    retInfor="推送到企业号时发生网络异常,异常信息:"+e.getMessage();
							}
						}else{
							        code="400";
									retInfor="推送到企业号时发生异常,异常信息:消息接收人员为空";
						}
				}else{
					code="250";
					retInfor="企业号推送开关设置没有打开";
				}
			}
		} catch (Exception e) {
			code="500";
			retInfor="推送消息时发生异常,异常信息:"+e.getMessage();
		} finally {
			if (post != null) {
                post.releaseConnection();
            }
		}
		retMap.put("code", code);
		retMap.put("retInfor", retInfor);
	   return retMap;
	}
}
