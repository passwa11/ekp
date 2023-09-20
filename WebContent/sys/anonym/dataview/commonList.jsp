<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ page import="com.landray.kmss.sys.anonym.context.AnonymContext"%>
<%@ page import="com.landray.kmss.sys.anonym.constants.SysAnonymConstant"%>
<%@ page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@ page import="com.landray.kmss.sys.anonym.forms.SysAnonymCommonForm"%>
<%@ page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>
<%@ page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ page import="com.landray.kmss.sys.portal.util.PortalUtil"%>
<%@ page import="com.landray.kmss.sys.ui.xml.model.*"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", -1);

JSONArray result = (JSONArray)request.getAttribute("result");
String formatKey = (String) request.getAttribute("formatKey");
if(SysAnonymConstant.SYS_UI_DEFAULT_LIST.equals(formatKey)){
response.setContentType("text/html");
PortalUtil.viewAnonymousPortalInfo(request);
%>
<jsp:include page="/sys/anonym/dataview/include/defaultList.jsp"></jsp:include>
<%
}else if(SysAnonymConstant.SYS_UI_LISTTABLE.equals(formatKey)){
%>
<jsp:include page="/sys/anonym/dataview/include/listTable.jsp"></jsp:include>
<%
}else{
	out.print(result.toJSONString());
}

%>



