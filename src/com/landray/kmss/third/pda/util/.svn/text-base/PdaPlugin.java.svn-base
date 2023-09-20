package com.landray.kmss.third.pda.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.third.pda.model.PdaTabViewConfig;
import com.landray.kmss.util.StringUtil;

public class PdaPlugin {
	// 扩展id
	private static final String ID = "com.landray.kmss.third.pda.extend";

	// 扩展项
	private static final String ITEM_PDA_EXTEND = "extend";

	// 模块名属性对应值
	public static final String PARAM_PDA_EXTEND_MODELNAME = "modelName";

	// 路径属性名对应值
	public static final String PARAM_PDA_EXTEND_JSP = "extendJsp";

	// 模版class
	public static final String PARAM_PDA_EXTEND_TEMPLATECLASS = "templateClass";

	// 创建URL
	public static final String PARAM_PDA_EXTEND_CREATEURL = "createURL";

	// 类别bean
	public static final String PARAM_PDA_EXTEND_CATEBEAN = "cateBean";

	// 图片bean
	public static final String PARAM_PDA_EXTEND_HEADBEAN = "headBean";

	// 内容bean
	public static final String PARAM_PDA_EXTEND_CONTENTBEAN = "contentBean";

	// 筛选URL
	public static final String PARAM_PDA_EXTEND_FILTERURL = "filterURL";

	public static final String PARAM_PDA_EXTEND_CREATETEMPURL = "createTempURL";

	private static Map<String, IExtension> extendMap = null;

	static {
		if (extendMap == null || extendMap.size() <= 0) {
			if (extendMap == null) {
                extendMap = new HashMap<String, IExtension>();
            }
			IExtension[] extensions = Plugin.getExtensions(ID, "*",
					ITEM_PDA_EXTEND);
			for (IExtension extension : extensions) {
				extendMap.put(Plugin.getParamValueString(extension,
						PARAM_PDA_EXTEND_MODELNAME), extension);
			}
		}
	}

	// 获取改模块额外的jsp展示页面
	public static String getPdaExtendJsp(String model) {
		return getPdaExtendInfo(model, PARAM_PDA_EXTEND_JSP);
	}

	/**
	 * 获取相应扩展信息
	 * 
	 * @param model
	 * @param param
	 * @return
	 */
	public static String getPdaExtendInfo(String model, String param) {
		return Plugin.getParamValueString(extendMap.get(model), param);
	}

	/**
	 * 特有函数，根据url找到modelClass进一步获取相关的plugin配置
	 * 
	 * @param request
	 *            http请求对象
	 * @param dataUrl
	 *            应为struts配置URL路径，如:"/km/review/km_review_main.do?method=view"
	 * @param param
	 *            PdaPlugin类中的param参数，多值用“;”分隔
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getPdaExtendInfo(
			HttpServletRequest request, String arguUrl, String param)
			throws Exception {
		Map<String, String> rtnMap = new HashMap<String, String>();
		if (!arguUrl.startsWith("/")) {
			String urlPrefix = StrutsAndDictCfgUtil.getUrlPrefix(request);
			if (arguUrl.startsWith(urlPrefix)) {
				arguUrl = arguUrl.substring(urlPrefix.length());
			} else {
                return rtnMap;
            }
		}
		String formClass = StrutsAndDictCfgUtil.getFormType(request, arguUrl);
		if (StringUtil.isNotNull(formClass)) {
			Object formObj = com.landray.kmss.util.ClassUtils.forName(formClass).newInstance();
			if (formObj instanceof IExtendForm) {
				String[] keys = param.split(";");
				for (String tmpKey : keys) {
					rtnMap.put(tmpKey, getPdaExtendInfo(
							((IExtendForm) formObj).getModelClass().getName(),
							tmpKey));
				}
			}
		}
		return rtnMap;
	}

	/**
	 * 通过某参数获取扩展点另外参数的值
	 * 
	 * @param param
	 * @param source
	 * @param target
	 * @return
	 */
	public static String getPdaParamByParam(String param, String source,
			String target) {
		Set<String> key = extendMap.keySet();
		IExtension extension = null;
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String k = it.next();
			extension = extendMap.get(k);
			if (param.equals(Plugin.getParamValueString(extension, source))) {
				break;
			}
		}
		return Plugin.getParamValueString(extension, target);
	}

	/**
	 * 根据modelName获取templateURL
	 * 
	 * @param request
	 * @param source
	 * @param target
	 * @return
	 * @throws Exception
	 */
	public static String getModelNameByTemplateURL(HttpServletRequest request,
			String source, String target) throws Exception {
		String templateURL = request.getRequestURL().toString();
		String modelName = null;
		String contextPath = request.getContextPath();
		templateURL = templateURL.substring(templateURL.indexOf(contextPath)
				+ contextPath.length());
		String formClass = StrutsAndDictCfgUtil.getFormType(request,
				templateURL);
		if (StringUtil.isNotNull(formClass)) {
			Object formObj = com.landray.kmss.util.ClassUtils.forName(formClass).newInstance();
			if (formObj instanceof IExtendForm) {
				modelName = getPdaParamByParam(((IExtendForm) formObj)
						.getModelClass().getName(), source, target);
			}
		}
		return modelName;
	}

	/**
	 * 根据request获取当前modelName
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getModelNameByReq(HttpServletRequest request)
			throws Exception {
		String url = request.getRequestURL().toString();
		String modelName = null;
		String contextPath = request.getContextPath();
		url = url.substring(url.indexOf(contextPath) + contextPath.length());
		String formClass = StrutsAndDictCfgUtil.getFormType(request, url);
		if (StringUtil.isNotNull(formClass)) {
			Object formObj = com.landray.kmss.util.ClassUtils.forName(formClass).newInstance();
			modelName = ((IExtendForm) formObj).getModelClass().getName();
		}
		return modelName;
	}

	private static final String TAB_ID = "com.landray.kmss.third.pda.tabView";
	private static final String ITEM_PDA_TAB = "tabView";
	public static final String PARAM_MODEL_NAME = "modelName";
	public static final String PARAM_PDA_TAB_NAME = "tabName";
	public static final String PARAM_PDA_TAB_URL = "tabUrl";
	public static final String PARAM_PDA_TAB_TYPE = "tabType";
	public static final String PARAM_PDA_TAB_ICON = "tabIcon";
	public static final String PARAM_PDA_TAB_ORDER = "tabOrder";
	private static final String PARAM_PDA_TAB_BEAN = "tabBean";
	public static final String PARAM_PDA_TAB_TEMPLATECLASS = "templateClass";
	private static List<PdaTabViewConfig> tabs = null;

	public static synchronized void initTabs() {
		tabs = new ArrayList<PdaTabViewConfig>();
		IExtensionPoint point = Plugin.getExtensionPoint(TAB_ID);
		IExtension[] extensions = point.getExtensions();
		for (IExtension extension : extensions) {
			if (ITEM_PDA_TAB.equals(extension.getAttribute("name"))) {
				PdaTabViewConfig params = new PdaTabViewConfig();
				params.setModel(extension.getModel());
				params.setTabNameMessageKey(Plugin.getParamValueString(
						extension, PARAM_PDA_TAB_NAME));
				params.setTabUrl(Plugin.getParamValueString(extension,
						PARAM_PDA_TAB_URL));
				params.setTabType(Plugin.getParamValueString(extension,
						PARAM_PDA_TAB_TYPE));
				params.setTabIcon(Plugin.getParamValueString(extension,
						PARAM_PDA_TAB_ICON));
				params.setTabOrder(Integer.valueOf(Plugin.getParamValueString(
						extension, PARAM_PDA_TAB_ORDER)));
				params.setModelName(Plugin.getParamValueString(extension,
						PARAM_MODEL_NAME));
				params.setTemplateClass(Plugin.getParamValueString(extension,
						PARAM_PDA_TAB_TEMPLATECLASS));
				params.setTabBean((IXMLDataBean) Plugin.getParamValue(
						extension, PARAM_PDA_TAB_BEAN));
				tabs.add(params);
			}
		}

		Collections.sort(tabs, new Comparator<PdaTabViewConfig>() {
			@Override
			public int compare(PdaTabViewConfig m1, PdaTabViewConfig m2) {
				if (m1.getTabOrder() < m2.getTabOrder()) {
					return 0;
				} else {
					return 1;
				}
			}
		});
	}

	public static List<PdaTabViewConfig> getTabs() {
		if (tabs == null) {
			initTabs();
		}
		return tabs;
	}

	public static List<PdaTabViewConfig> getTabsByModel(String model) {
		List<PdaTabViewConfig> tabsList = getTabs();
		List<PdaTabViewConfig> tabm = new ArrayList<PdaTabViewConfig>();
		for (PdaTabViewConfig pdaTabPlugin : tabsList) {
			if (pdaTabPlugin.getModel().equals(model)) {
				tabm.add(pdaTabPlugin);
			}
		}
		return tabm;
	}

}
