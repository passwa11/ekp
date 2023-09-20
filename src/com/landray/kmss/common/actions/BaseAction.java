package com.landray.kmss.common.actions;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.landray.kmss.sys.authentication.util.StringUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.Globals;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.action.ActionMessages;

/**
 * Action基类，不建议直接继承，仅当ExtendAction完全无法满足实际业务需求时才继承该类。<br>
 * 使用范围：Action层代码，作为基类继承。
 * 
 * @author 叶中奇
 * @version 1.0 2006-04-02
 */
public abstract class BaseAction extends KmssMultiActionController {
	protected static Logger log = org.slf4j.LoggerFactory.getLogger(BaseAction.class);

	protected void saveMessages(HttpServletRequest request,
			ActionMessages messages) {
		if ((messages == null) || messages.isEmpty()) {
			request.removeAttribute(Globals.MESSAGE_KEY);
			return;
		}
		request.setAttribute(Globals.MESSAGE_KEY, messages);
	}

	protected void saveErrors(HttpServletRequest request,
			ActionMessages errors) {
		if ((errors == null) || errors.isEmpty()) {
			request.removeAttribute(Globals.ERROR_KEY);
			return;
		}
		request.setAttribute(Globals.ERROR_KEY, errors);
	}

	/**
	 * 根据spring配置的业务对象名称，取得业务对象实例。
	 * 
	 * @param name
	 *            spring配置的业务对象名称
	 * @return spring的业务对象（service），在安全模式下，由于部分service没有加载，所以可能返回null
	 */
	protected Object getBean(String name) {
	    ApplicationContext springApplicationContext = getSpringApplicationContext();
	    if(springApplicationContext==null){
	        return null;
	    }
	    try{
	        return springApplicationContext.getBean(name);
	    }catch(BeansException be){
	        String startMode = String.valueOf(((WebApplicationContext)springApplicationContext).getServletContext().getInitParameter("pluginMode"));
	        if("safe".equalsIgnoreCase(startMode)){
	            //安全模式下，允许某些类找不到
	            if(log.isWarnEnabled()){
	                log.warn("Safe mode",be);
	            }
	            return null;
	        }else{
	            throw new RuntimeException(be);
	        }
	    }
	}

	protected ApplicationContext getSpringApplicationContext() {
	    ApplicationContext context = null;
	    try{
	        //SpringBeanUtil.setApplicationContext
	        context = getApplicationContext(); 
	    }catch(Exception e){
	        if(log.isWarnEnabled()){
	            log.warn("SpringMVC context is not ready:"+e.getMessage());
	        }
	        context = SpringBeanUtil.getApplicationContext();
	        if(context!=null){
	            if(log.isWarnEnabled()){
	                log.warn("Use context: "+context.getDisplayName()+", type: "+context.getClass().getName());
	            }
	        }else{
	            if(log.isWarnEnabled()){
                    log.warn("There is not available ApplicationContext instance, 'getBean()' would return NULL when initialization."
                            + " Invoke 'getBean()' again in runtime is recommended.");
                }
	        }
	    }
		return context;
	}

	protected final Map<?, ?> getKeyMethodMap() {
		return null;
	}

	@Override
	protected String getMethodName(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response,
								   String parameter) throws Exception {
		String keyName = request.getParameter(parameter);
		if (keyName == null || keyName.length() == 0) {
			String method = (String) request.getAttribute("method_0");
			if (StringUtil.isNotNull(method)) {
				return method;
			}
			return null;
		}
		String methodName = getLookupMapName(request, keyName, mapping);
		return methodName;
	}

	@Override
	protected String getLookupMapName(HttpServletRequest request,
									  String keyName, ActionMapping mapping) throws ServletException {
		return keyName;
	}

}
