<%@page import="com.landray.kmss.constant.SysDocConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ page import="com.landray.kmss.sys.iassister.util.AssisterUtils"%>
<%@ page
	import="com.landray.kmss.sys.iassister.service.ISysIassisterTemplateService"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<mui:cache-file name="mui-sys-iassister.js" cacheType="md5"/>
<mui:cache-file name="mui-iassister-dialog.css" cacheType="md5"/>

<c:set var="hasAuth" value="false"></c:set>
<kmss:authShow roles="ROLE_SYSIASSISTER_DEFAULT">
	<c:set var="hasAuth" value="true"></c:set>
</kmss:authShow>
<c:set var="modelForm" value="${requestScope[param.formName]}" />
<c:set var="lblMsgKey" value="sys-iassister:msg.check_items.label"></c:set>
<c:if test="${not empty param.messageKey }">
	<c:set var="lblMsgKey" value="${param.messageKey }"></c:set>
</c:if>
<c:set var="draftStatus" value="<%=SysDocConstant.DOC_STATUS_DRAFT%>"></c:set>
<%
	String hasCheckItem = "false";
	try {
		JSONObject rtnData = new JSONObject();
		//获取模板的配置项信息
		JSONObject params = new JSONObject().fluentPut("templateId", request.getParameter("templateId"))
				.fluentPut("templateModelName", request.getParameter("templateModelName"))
				.fluentPut("mainModelId", request.getParameter("mainModelId"));
		rtnData = AssisterUtils.getService(ISysIassisterTemplateService.class).getTemplateInfo(params);
		if (rtnData != null) {
			hasCheckItem = rtnData.getString("hasCheckItems");
		}

	} catch (Exception e) {
	}
	pageContext.setAttribute("_hasCheckItem", hasCheckItem);
%>
<c:if test="${hasAuth && _hasCheckItem eq 'true' }">
	<li data-dojo-type="sys/iassister/mobile/js/tabbarButton" class="lbpmSwitchButton muiSplitterButton" 
	data-dojo-props='icon1:"fontmuis muis-iassister",ias_params:{templateId:"${param.templateId}",
	templateModelName:"${param.templateModelName}",mainModelId:"${param.mainModelId}",mainModelName:"${param.mainModelName}",
	resultTitle:"${lfn:message(lblMsgKey)}",draftStatus:"${draftStatus}",panelId:"${param.panelId}",idx:"${param.idx}",
	hasAuth:"${hasAuth}",hasCheckItems:"${_hasCheckItem}",fdKey:"${param.fdKey}"}'>
		<bean:message bundle="sys-iassister" key="mui.button.iassister" />
	</li>
</c:if>