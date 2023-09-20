package com.landray.kmss.common.rest.controller;

import com.landray.kmss.util.*;
import com.landray.kmss.web.RestResponse;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ActionConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 基础controller类
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
public abstract class BaseController {

	protected final ActionMapping emptyMapping = new ActionMapping(new ActionConfig());

	protected final Log log = LogFactory.getLog(BaseController.class);

	protected <T> RestResponse<T> result(HttpServletRequest request) {
		return result(request, null, null);
	}

	protected <T> RestResponse<T> result(HttpServletRequest request, T okResult) {
		return result(request, okResult, null);
	}

	protected <T> RestResponse<T> result(HttpServletRequest request, T okResult, String msg) {
		KmssReturnPage returnPage = KmssReturnPage.getInstance(request);
		KmssMessages messages = returnPage.getMessages();
		if (messages != null && messages.hasError()) {
			// 请求失败且有报错信息的返回
			KmssMessageWriter messageWriter = new KmssMessageWriter(request, returnPage);
			List<KmssMessage> messageList = messages.getMessages();
			if (!ArrayUtil.isEmpty(messageList)) {
				KmssMessage message = messageList.get(0);
				String messageInfo = messageWriter.getMessageInfo(message);
				log.error(messageInfo, message.getThrowable());
				return RestResponse.error(messageInfo + message.getThrowable());
			}
		} else {
			// 请求成功的返回
			if (msg == null) {
				msg = "请求成功";
			}
			if (log.isDebugEnabled()) {
				log.debug(msg);
			}
			return RestResponse.ok(okResult, msg);
		}
		// 请求失败的返回
		if (msg == null) {
			msg = "请求失败";
		}
		log.error(msg);
		return RestResponse.error(msg);
	}

	/**
	 * 获取默认的Form的类名，用于从request的attribute中取出form对象
	 */
	protected String getFormName(Object form) {
		String formName = ModelUtil.getModelClassName(form);
		formName = formName.substring(formName.lastIndexOf('.') + 1);
		return formName.substring(0, 1).toLowerCase() + formName.substring(1);
	}

	/**
	 * 获取一个默认的Mapping，用于rest请求时，无需构造spring-mvc的返回对象
	 */
	protected ActionMapping getDefMapping() {
		return emptyMapping;
	}

	/**
	 * 根据class类型从spring容器中找到对应的Bean
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getBeansForType(Class<T> cls) {
		List<T> beansForType = SpringBeanUtil.getBeansForType(cls);
		if (beansForType != null && beansForType.size() > 0) {
			return beansForType.get(0);
		}
		throw new NoSuchBeanDefinitionException(cls);
	}
}
