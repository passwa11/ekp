<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.web.filter.ResourceCacheFilter"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil,net.sf.json.*"%>
<%
//设置缓存
long expires = 7 * 24 * 60 * 60;
long nowTime = System.currentTimeMillis();
response.addDateHeader("Last-Modified", Long.parseLong(ResourceCacheFilter.mobileCache));
response.addDateHeader("Expires", nowTime + expires * 1000);
response.addHeader("Cache-Control", "max-age=" + expires);

	try {
		String bundle = request.getParameter("bundle");
		String starts = "mui.";
		Boolean isAll =  false;
		if (bundle.indexOf(':') > -1) {
			String[] sps = bundle.split(":");
			bundle = sps[0];
			starts = sps[1];
		}
		//若没有加*号默认是“mui.”，为了兼容之前已经有的调用代码，用*号表示获取整个资源文件
		if("*".equals(starts)) {
			isAll = true;
		}
		String resource = ResourceUtil.APPLICATION_RESOURCE_NAME;
		if(StringUtil.isNotNull(bundle)){
			resource = "com.landray.kmss."
				+ bundle.replaceAll("-", ".") + "."
				+ ResourceUtil.APPLICATION_RESOURCE_NAME;
		}
		Method method = ResourceUtil.class
				.getDeclaredMethod("getLocaleByUser");
		method.setAccessible(true);
		Locale locale = (Locale) method.invoke(ResourceUtil.class);
		
		// 将bundle转换为模块路径
		String urlPrefix = "";
		if (StringUtil.isNull(bundle)) {
			urlPrefix = "/";
		} else {
			urlPrefix = "/" + bundle.replaceAll("-", "/") + "/";
		}
		// 保存所有资源 
		JSONObject keyMap = new JSONObject();
		// 取附件目录的资源
		Map<String, String> newMsgs = com.landray.kmss.sys.profile.util.ResourceBundle.getInstance().getStringByModule(urlPrefix, locale);
		if (newMsgs != null) {
			for(String key : newMsgs.keySet()) {
				if(isAll) {
					keyMap.accumulate(key, newMsgs.get(key));
				} else {
					if (key.startsWith(starts))
						keyMap.accumulate(key, newMsgs.get(key));
				}
			}
		}
		// 取原始JAR包资源
		ResourceBundle resourceBundle = (locale == null) ? ResourceBundle.getBundle(resource) : ResourceBundle.getBundle(resource, locale);
		if (resourceBundle != null) {
			Enumeration<String> keys = resourceBundle.getKeys();
			if(isAll) {
				for (; keys.hasMoreElements();) {
					String key = keys.nextElement();
					if(!keyMap.containsKey(key)) // 不能覆盖附件目录的信息
						keyMap.accumulate(key, resourceBundle.getString(key));
				}
			} else {
				for (; keys.hasMoreElements();) {
					String key = keys.nextElement();
					if (key.startsWith(starts) && !keyMap.containsKey(key)) { // 不能覆盖附件目录的信息
						keyMap.accumulate(key, resourceBundle.getString(key));
					}
				}
			}
		}
		out.print(keyMap);
	} catch (Exception e) {
		e.printStackTrace();
		out.print("['error']");
	}
%>