package com.landray.kmss.third.pda.util;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.config.IActionConfig;
import com.landray.kmss.web.config.ModuleConfig;
import com.landray.kmss.web.taglib.xform.TagUtils;
import com.landray.kmss.web.util.ModuleUtils;

public abstract class StrutsAndDictCfgUtil {
	private static String getServletPath(HttpServletRequest request,
			String preServletPath) {
		if (StringUtil.isNotNull(preServletPath)) {
			return preServletPath;
		} else {
			return request.getServletPath();
		}
	}

	private static IActionConfig getActionConfig(HttpServletRequest request,
			String requestActionPath) {
		ModuleConfig moduleCfg = ModuleUtils.getInstance()
				.getModuleConfig(request);
		String servletPath = (String) getServletPath(request, requestActionPath);
		return moduleCfg.findActionConfig(servletPath);
	}

	/******
	 * 获取模块的路径
	 * 
	 * @param request
	 * @return
	 *****/
	public static String getModulePre(String requestActionPath) {
		String servletPath = requestActionPath;
		String[] paths = servletPath.split("/");
		if (paths.length < 3) {
            return null;
        }
		String modulePath = paths[1] + "/" + paths[2];
		SysConfigs sysCfg = SysConfigs.getInstance();
		SysCfgModule module = sysCfg.getModule("/" + modulePath + "/");
		if (module == null && paths.length > 3) {
			modulePath = paths[1] + "/" + paths[2] + "/" + paths[3];
			module = sysCfg.getModule("/" + modulePath + "/");
		}
		return module != null ? modulePath : null;
	}

	/***
	 * 获取struts.xml中action中定义的FormName
	 * 
	 * @param request
	 * @return
	 ***/
	public static String getFormName(HttpServletRequest request,
			String requestActionPath) {
		IActionConfig actCfg = getActionConfig(request, requestActionPath);
		return actCfg == null ? null : actCfg.getFormName();
	}

	/***
	 * 获取Struts.xml中配置的form的class类型
	 * 
	 * @param request
	 * @return
	 ***/
	public static String getFormType(HttpServletRequest request,
			String requestActionPath) {
		IActionConfig actCfg = getActionConfig(request, requestActionPath);
		return actCfg == null ? null : actCfg.getFormType();
	}

	/***
	 * 获取request中form对象,变量名先去action中配置的name属性,
	 * 若无,则根据form的类型按照ekp规则,首字母小写获取form变量名
	 * 
	 * @param request
	 * @return
	 ***/
	public static IExtendForm getForm(HttpServletRequest request,
			String formName) {
		Object obj = null;
		if (StringUtil.isNotNull(formName)) {
			obj = request.getAttribute(formName);
			if (obj == null) {
				obj = TagUtils.getFormBean(request);
			}
		}
		return (IExtendForm) obj;
	}

	/****
	 * 根据form对象,定位model获取ModelToFormMap
	 * 
	 * @param request
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 ****/
	public static ModelToFormPropertyMap getModelToFormMap(IExtendForm formObj)
			throws InstantiationException, IllegalAccessException {
		IBaseModel model = null;
		if (formObj != null) {
			model = (IBaseModel) (formObj.getModelClass().newInstance());
			return model.getToFormPropertyMap();
		}
		return null;
	}
	
	/**
	 * 获取服务器URL前缀
	 * @param request
	 * @return
	 */
	public static String getUrlPrefix(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String dns = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() != 80) {
            dns += ":" + request.getServerPort();
        }
		if (StringUtil.isNotNull(contextPath)) {
            return dns + contextPath;
        }
		return dns;
	}
	
}
