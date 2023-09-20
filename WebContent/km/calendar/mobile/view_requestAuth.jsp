<%@ page import="com.landray.kmss.km.calendar.model.KmCalendarRequestAuth" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.alibaba.fastjson.JSONArray" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	KmCalendarRequestAuth kmCalendarRequestAuth = (KmCalendarRequestAuth) request.getAttribute("kmCalendarAuth");
	String fdRequestAuth = kmCalendarRequestAuth.getFdRequestAuth();
	//构造checkBox所需要的JSON
	JSONArray store = new JSONArray();
	JSONObject readStoreItem = new JSONObject();
	readStoreItem.put("text", ResourceUtil.getString("kmCalendarRquestAuth.fdRequestAuth.authRead","km-calendar"));
	readStoreItem.put("value","authRead");
	readStoreItem.put("checked",fdRequestAuth.contains("authRead"));
	store.add(readStoreItem);

	JSONObject editStoreItem = new JSONObject();
	editStoreItem.put("text", ResourceUtil.getString("kmCalendarRquestAuth.fdRequestAuth.authEdit","km-calendar"));
	editStoreItem.put("value","authEdit");
	editStoreItem.put("checked",fdRequestAuth.contains("authEdit"));
	store.add(editStoreItem);

	JSONObject modifyStoreItem = new JSONObject();
	modifyStoreItem.put("text", ResourceUtil.getString("kmCalendarRquestAuth.fdRequestAuth.authModify","km-calendar"));
	modifyStoreItem.put("value","authModify");
	modifyStoreItem.put("checked",fdRequestAuth.contains("authModify"));
	store.add(modifyStoreItem);

	request.setAttribute("store",store.toString());
%>
<template:include ref="mobile.view" >
	<template:replace name="title">
		<bean:message bundle="km-calendar" key="kmCalendarRequestAuth.persons" />
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/mobile/resource/css/view.css?s_cache=${MUI_Cache}" />
		<style>
			.txtContent {
			    text-align: center;
	    		padding: 1.5rem 0px;
	    	}
    	</style>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView" class="gray" data-dojo-type="mui/view/DocScrollableView">
			<div class="muiFormContent kmCalendarFormContent">
				<p class="txtContent">
					<bean:message bundle="km-calendar" key="kmCalendarRequestAuth.persons" />
				</p>
				<table class="muiSimple" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<div class="newMui muiFormEleWrap muiFormGroup showTitle muiFormStatusView muiFormCheckBoxNormal muiFormLeft">
								<div class="muiFormEleTip">
									<span class="muiFormEleTitle"><bean:message bundle="km-calendar" key="kmCalendarRequestAuth.fdRequestPerson" /></span>
								</div>
								<div class="muiFormItem">
									<c:out value="${kmCalendarRequestAuthForm.fdRequestPersonNames}" />
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div data-dojo-type='mui/form/CheckBoxGroup'
								data-dojo-props='"name":"fdRequestAuth",
								"mul":true,"concentrate":false,
								"showStatus":"view",
								"alignment":"V",
								"orient":"vertical",
								"subject":"<bean:message key="kmCalendarRequestAuth.fdRequestAuth" bundle="km-calendar"/>",
								"store":<%=request.getAttribute("store")%>'>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</template:replace>
</template:include>