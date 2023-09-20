<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/sys/ui/jsp/common.jsp"%>

<ui:menu layout="sys.ui.menu.nav" id="simplecategoryId">
	<ui:menu-item text="${ lfn:message('home.home') }"
		icon="lui_icon_s_home"
		href="javascript:top.open('${LUI_ContextPath }/','_self')"
		target="_self">
		
	</ui:menu-item>

	<%
		String title = request.getParameter("moduleTitle");

			if (StringUtil.isNull(title)) {
				title = StringEscapeUtils.escapeJavaScript(title);
				String modelName = request.getParameter("modelName");

				if (StringUtil.isNotNull(modelName)) {
					modelName = StringEscapeUtils.escapeJavaScript(modelName);
					title = ResourceUtil.getString(SysDataDict.getInstance().getModel(modelName).getMessageKey());
				}
			}

			if (StringUtil.isNotNull(title)) {
	%>
	<ui:menu-item text="<%=title%>" target="_self">
	</ui:menu-item>

	<%
		}
	%>
	<ui:menu-item text="${param.title }" target="_self">
	</ui:menu-item>

	<%-- <c:import url="/kms/lservice/index/common/switch.jsp"
		charEncoding="utf-8">

		<c:param name="modelName" value="${param.modelName }" />
		<c:param name="type" value="${param.type }"></c:param>

	</c:import> --%>
</ui:menu>

<script>
	seajs.use('kms/lservice/index/js/index.js');
</script>
