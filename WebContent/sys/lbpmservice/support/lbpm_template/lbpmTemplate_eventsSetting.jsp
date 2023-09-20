<%@page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@page import="com.landray.kmss.util.UserUtil" %>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@page import="com.landray.kmss.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang" %>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextareaGroupTag" %>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant" %>
<%
    pageContext.setAttribute("isLangSuportEnabled", MultiLangTextareaGroupTag.isLangSuportEnabled());
%>
<script>
    
</script>
<template:include file="/sys/profile/resource/template/list.jsp">
    <template:replace name="title">${ lfn:message('sys-lbpmservice-support:table.lbpmEventsLog') }</template:replace>
    <template:replace name="content">
        <ui:tabpanel layout="sys.ui.tabpanel.list">
            <ui:content title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.eventsSetting') }">
                <c:import url="/sys/lbpmservice/support/lbpm_template/lbpmTemplate_eventSetting.jsp">
                   <c:param name="prefix" value="${JsParam.param}">
                    </c:param>
                    <c:param name="fdProcessTemplateId" value="${JsParam.fdProcessTemplateId}">
                    </c:param>
                </c:import>
            </ui:content>
            <ui:content title="${ lfn:message('sys-lbpmservice-support:lbpmEventsLog.eventsLog') }">
				<c:import url="/sys/lbpmservice/support/lbpm_events_log/lbpmEventsLog_list.jsp">
                    <c:param name="fdProcessTemplateId" value="${JsParam.fdProcessTemplateId}">
                    </c:param>
                </c:import>
			</ui:content>
        </ui:tabpanel>

    </template:replace>
</template:include>