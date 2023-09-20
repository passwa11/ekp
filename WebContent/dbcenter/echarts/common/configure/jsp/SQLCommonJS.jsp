<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
	// 用于多语言，方便js脚本中获取翻译
	var DbcenterLang = {};
	<%
	try {
		String resource = "com.landray.kmss.dbcenter.echarts."
				+ ResourceUtil.APPLICATION_RESOURCE_NAME;

		Locale locale = ResourceUtil.getLocaleByUser();

		ResourceBundle resourceBundle = (locale == null) ? ResourceBundle
				.getBundle(resource)
				: ResourceBundle.getBundle(resource, locale);
		if (resourceBundle != null) {
			Enumeration<String> keys = resourceBundle.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				if (key.startsWith("DbcenterLang.")) {
					out.println(key + " = \""
							+ resourceBundle.getString(key).replaceAll("\"", "\\\\\"") + "\";");
				}
			}
		}

	} catch (Exception e) {
		e.printStackTrace();
		out.print("DbcenterLang.Error = 'error'");
	}
%>
	
	
	Com_IncludeFile("SQLStructure.js", "${LUI_ContextPath}/dbcenter/echarts/common/configure/js/", "js", true);
	Com_IncludeFile("SQLDataSource.js", "${LUI_ContextPath}/dbcenter/echarts/common/configure/js/", "js", true);
	Com_IncludeFile("SQLJSONComponent.js", "${LUI_ContextPath}/dbcenter/echarts/common/configure/js/", "js", true);
	Com_IncludeFile("SQLComponent.js", "${LUI_ContextPath}/dbcenter/echarts/common/configure/js/", "js", true);
	Com_IncludeFile("SQLCache.js", "${LUI_ContextPath}/dbcenter/echarts/common/configure/js/", "js", true);
	Com_IncludeFile('SQLSelectValue.js',Com_Parameter.ContextPath+'dbcenter/echarts/common/configure/js/','js',true);
	Com_IncludeFile('SQLWhereCondition.js',Com_Parameter.ContextPath+'dbcenter/echarts/common/configure/js/','js',true);
	Com_IncludeFile('SQLFilterItem.js',Com_Parameter.ContextPath+'dbcenter/echarts/common/configure/js/','js',true);

	Com_IncludeFile('SQLColStats.js',Com_Parameter.ContextPath+'dbcenter/echarts/common/configure/js/','js',true);
	Com_IncludeFile('SQLRowStats.js',Com_Parameter.ContextPath+'dbcenter/echarts/common/configure/js/','js',true);
</script>