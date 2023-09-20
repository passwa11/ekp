package com.landray.kmss.common.rest.controller;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.config.ModuleConfig;
import com.landray.kmss.web.taglib.TagUtils;
import com.landray.kmss.web.util.ModuleUtils;
import com.landray.kmss.web.util.RequestUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 用于跳转到页面的前后端分离Controller继承，针对页面所需的内容进行了处理
 *
 * @Author 严明镜
 * @create 2020年12月03日
 */
public class ForwardController extends BaseController {

	protected static final String ACTION_NAME_KEY = "actionName";
	protected static final String FAILURE_URL = "/resource/jsp/error.jsp";

	private final Log logger = LogFactory.getLog(ForwardController.class);

	/**
	 * 获得一个Action对应的Form的实例
	 */
	protected ActionForm getFormInstance(HttpServletRequest request, ExtendAction action) throws Exception {
		ActionForm instance = createActionForm(action);
		instance.reset(getActionMapping(request, action), request);
		RequestUtils.populate(instance, request);
		if ("GET".equals(request.getMethod())) {
			String method = request.getParameter("method");
			fillMethodGetToBaseForm(instance, method);
		}
		request.setAttribute(getFormName(instance), instance);
		request.setAttribute(com.landray.kmss.web.taglib.xform.TagUtils.FORM_BEAN, instance);
		return instance;
	}

	/**
	 * 填充Form中的setMethod_GET字段值
	 */
	protected void fillMethodGetToBaseForm(Object formInstance, String method) {
		try {
			Class<?> formClass = formInstance.getClass();
			Method setMethod_GET = formClass.getMethod("setMethod_GET", String.class);
			setMethod_GET.invoke(formInstance, method);
		} catch (NoSuchMethodException e) {
			//ignored
		} catch (Exception e) {
			logger.warn("Can not invoke setMethod_GET for formClass:" + formInstance.getClass().getName(), e);
		}
	}

	/**
	 * 获取action对应在spring-mvc中配置的映射，用于rest入口跳转jsp时，JSP标签能正常获取到action的信息
	 */
	protected ActionMapping getActionMapping(HttpServletRequest request, ExtendAction action) {
		String[] beanNamesForType = SpringBeanUtil.getApplicationContext().getBeanNamesForType(action.getClass());
		if (beanNamesForType.length <= 0) {
			return emptyMapping;
		}
		String beanName = beanNamesForType[0];
		ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(request);
		String mappingName = TagUtils.getInstance().getActionMappingName(beanName);
		return (ActionMapping) moduleConfig.findActionConfig(mappingName);
	}

	protected ExtendAction getAction(HttpServletRequest request) {
		String actionName = request.getParameter(ACTION_NAME_KEY);
		if (StringUtil.isNull(actionName)) {
			throw new IllegalArgumentException("Required parameter '" + actionName + "'.");
		}
		try {
			Class<?> actionClass = ClassUtils.forName(actionName);
			if (!ExtendAction.class.isAssignableFrom(actionClass)) {
				throw new IllegalArgumentException("Bean '" + actionName + "' is not extends ExtendAction.");
			}
			return (ExtendAction) getBeansForType(actionClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Bean '" + actionName + "' not found.");
		}
	}

	protected void forward(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
		KmssReturnPage returnPage = KmssReturnPage.getInstance(request);
		KmssMessages messages = returnPage.getMessages();
		if (messages != null && messages.hasError()) {
			request.getRequestDispatcher(FAILURE_URL).forward(request, response);
		}
		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * reset方法会重置fdId,为了保持fdId一致，用代理方式在reset执行完后，重新将请求的fdId设置到form
	 */
	protected ActionForm createActionForm(ExtendAction action) throws Exception{
		Enhancer enhancer = new Enhancer();
		Class<?> clazz = action.getFormClass();
		//设置目标类的字节码文件
		enhancer.setSuperclass(clazz);
		//设置回调函数
		enhancer.setCallback(new FormResetMethodInterceptor());
		//这里的creat方法就是正式创建代理类
		return (ActionForm) enhancer.create();
	}

	/**
	 * 拦截reset方法,重新将请求的fdId设置到form
	 */
	static class FormResetMethodInterceptor implements MethodInterceptor {
		@Override
		public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
			Object object = methodProxy.invokeSuper(proxy, args);
			String methodName = method.getName();
			if (proxy instanceof ExtendForm && "reset".equals(methodName)
					&& args != null && args.length == 2 && args[1] instanceof HttpServletRequest) {
				ExtendForm extendForm = (ExtendForm) proxy;
				HttpServletRequest request = (HttpServletRequest) args[1];
				RequestUtils.populate(extendForm, request);
			}
			return object;
		}
	}

}
