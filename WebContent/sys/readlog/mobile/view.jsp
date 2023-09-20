<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.sys.authentication.user.KMSSUser"%>
<%
	KMSSUser user = UserUtil.getKMSSUser(request);
	request.setAttribute("user", user);
%>
<%-- 导航头部 --%>
<div data-dojo-type="mui/header/Header" data-dojo-props="height:'4.4rem'" class="muiHeaderNav">
    <%-- Tab页签 --%>
	<div data-dojo-type="sys/readlog/mobile/js/nav/AccessLogCfgNavBar" 
		data-dojo-props="modelId:'${param.modelId }',modelName:'${param.modelName }'"> 
	</div>
</div>

<%--  页签内容展示区域，可纵向上下滑动   --%>
<div data-dojo-type="mui/list/NavView">
	<ul class="muiAccessLogList"
		data-dojo-type="mui/list/HashJsonStoreList" 
		data-dojo-mixins="sys/readlog/mobile/js/list/AccessLogItemListMixin"
		data-dojo-props="modelId:'${param.modelId }',modelName:'${param.modelName }'">
	</ul>
</div>
