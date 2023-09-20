<%@ page language="java" contentType="application/x-javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.lang.reflect.*,com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@page import="com.landray.kmss.sys.lbpm.engine.task.NodeInstanceUtils"%>

<%
	Method method = ResourceUtil.class
			.getDeclaredMethod("getLocaleByUser");
	method.setAccessible(true);
	Locale locale = (Locale) method.invoke(ResourceUtil.class);
	if (locale != null) {
		out.println("FlowChartObject.Lang.Locale = '" + locale
				+ "';");
	}

out.println("var _isLangSuportEnabled = "+MultiLangTextGroupTag.isLangSuportEnabled()+";");
out.println("var _langJson = "+MultiLangTextGroupTag.getLangsJsonStr()+";");
out.println("var _userLang = \""+MultiLangTextGroupTag.getUserLangKey()+"\";");
out.println("var _allNodeName = "+NodeInstanceUtils.getAllNodeNameSupportLangText()+";");
//System.out.println("var _allNodeName = "+NodeInstanceUtils.getAllNodeNameSupportLangText()+";");

%>