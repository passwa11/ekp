package com.landray.kmss.third.pda.interfaces;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.sys.mobile.annotation.Separater;
import com.landray.kmss.third.pda.util.PdaFlagUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.config.IActionConfig;

/**
 * 三级页面分离
 */
public class ThirdPdaSeparateService implements InitializingBean, ApplicationContextAware {

	private ApplicationContext applicationContext;

	private static Map<String, String> mobileUrls = new HashMap<>();
	private static Map<String, String> dataUrls = new HashMap<>();
	private static Map<String, String> viewNames = new HashMap<>();

	/**
	 * 黑名单
	 */
	private static final String[] blacklist = { "method", "compatibleMode" };

	/**
	 * +.do后缀
	 * 
	 * @param url
	 * @return
	 */
	public static String formatUrlBySuffix(String url) {
		if (!url.endsWith(IActionConfig.DEFAULT_URL_SUFFIX)) {
			url = url + IActionConfig.DEFAULT_URL_SUFFIX;
		}
		return url;
	}

	/**
	 * 格式化首个问号
	 * 
	 * @param url
	 * @return
	 */
	public static String formatUrlByParam(String url) {
		if (url.indexOf("?") < 0) {
			url = url.replaceFirst("&", "?");
		}
		return url;
	}

	/**
	 * 链接参数格式化
	 * 
	 * @param url
	 * @param request
	 * @return
	 */
	private static String resolveUrl(String url, HttpServletRequest request) {
		if (StringUtil.isNull(url)) {
			return null;
		}
		// 拷贝参数
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {

			String name = (String) names.nextElement();
			if (ArrayUtils.contains(blacklist, name)) {
				continue;
			}
			String[] parameterValue = request.getParameterValues(name);
			for (String value : parameterValue) {
				url = StringUtil.setQueryParameter(url, name, value);
			}
		}

		return formatUrlByParam(url);
	}

	/**
	 * 获取移动端映射链接
	 * 
	 * @param url
	 * @return
	 */
	public static String getMobileUrl(String url, HttpServletRequest request) {
		if (!PdaFlagUtil.checkClientIsPda(request)) {
			return null;
		}
		return resolveUrl(mobileUrls.get(url), request);
	}

	/**
	 * 获取数据源映射链接
	 * 
	 * @param url
	 * @return
	 */
	public static String getDataUrl(String url, HttpServletRequest request) {
		return resolveUrl(dataUrls.get(url), request);
	}

	/**
	 * 获取映射视图名称
	 * @param dataUrl
	 * @return
	 */
	public static String getViewName(String dataUrl){
		String modelUrl = mobileUrls.get(dataUrl);
		if(StringUtil.isNull(modelUrl)) {
            return null;
        }
		return viewNames.get(modelUrl);
	}

	/**
	 * 格式化源路径
	 * @param httpRequest
	 * @param method
	 * @return
	 */
	public static String formatDataUrl(HttpServletRequest httpRequest, String method){
		String url = httpRequest.getRequestURI().replaceFirst(httpRequest.getContextPath(), "");
		// 对于?前的;参数作处理,有些情况 会传;jessionid=xxxxx过来
		String[] split = url.split(";");
		url = split[0];
		// +.do后缀
		url = ThirdPdaSeparateService.formatUrlBySuffix(url);
		// 设置方法名
		url = StringUtil.setQueryParameter(url, "method", method);
		// 格式化问号
		url = ThirdPdaSeparateService.formatUrlByParam(url);
		return url;
	}

	/**
	 * 初始化所有带@Separater注解的方法
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

		Map<String, Object> separaters = applicationContext.getBeansWithAnnotation(Separater.class);

		Iterator<String> keys = separaters.keySet().iterator();

		while (keys.hasNext()) {
			String key = keys.next();
			Object separater = separaters.get(key);
			if (!(separater instanceof BaseAction)) {
				continue;
			}

			BaseAction action = (BaseAction) separater;

			Method[] methods = action.getClass().getMethods();
			String url = action.getPath();

			for (Method method : methods) {
				Separater separaterMethod = AnnotationUtils.findAnnotation(method, Separater.class);
				if (separaterMethod == null) {
					continue;
				}
				// +.do后缀
				String orignUrl = formatUrlBySuffix(url);
				// +方法名
				orignUrl = StringUtil.setQueryParameter(orignUrl, action.getParameter(), method.getName());
				// 格式化首个问号
				orignUrl = formatUrlByParam(orignUrl);
				// 移动端伪分离页面
				String modelUrl = separaterMethod.value();
				String viewName = separaterMethod.viewName();
				// 双向
				mobileUrls.put(orignUrl, modelUrl);
				dataUrls.put(modelUrl, orignUrl);
				viewNames.put(modelUrl,viewName);
			}

		}

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
