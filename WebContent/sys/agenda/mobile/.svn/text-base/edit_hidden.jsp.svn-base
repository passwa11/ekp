<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request" />
<c:set var="sysAgendaMainForm" value="${mainModelForm.sysAgendaMainForm}" scope="request" />
<c:set var="sysNotifyRemindMainContextForm" value="${mainModelForm.sysNotifyRemindMainContextForm}" scope="request" />

<html:hidden property="syncDataToCalendarTime" value="${mainModelForm.syncDataToCalendarTime}"/>
<c:if test="${mainModelForm.syncDataToCalendarTime=='flowSubmitAfter' || mainModelForm.syncDataToCalendarTime=='flowPublishAfter'}">
	<html:hidden property="sysAgendaMainForm.fdSubjectFieldName" />
	<html:hidden property="sysAgendaMainForm.fdBeginTimeFieldName" />
	<html:hidden property="sysAgendaMainForm.fdEndTimeFieldName" />
	<html:hidden property="sysAgendaMainForm.fdNotifierIdFieldName" />
	<html:hidden property="sysAgendaMainForm.fdLocateFieldName" />
	<html:hidden property="sysAgendaMainForm.fdConditionFieldName" />
	<html:hidden property="sysAgendaMainForm.fdNotifierSelectType" />
	
	<html:hidden property="sysAgendaMainForm.fdSubjectFieldFormula" />
	<html:hidden property="sysAgendaMainForm.fdBeginTimeFieldFormula" />
	<html:hidden property="sysAgendaMainForm.fdEndTimeFieldFormula" />
	<html:hidden property="sysAgendaMainForm.fdNotifierIdFieldFormula" />
	<html:hidden property="sysAgendaMainForm.fdLocateFieldFormula" />
	<html:hidden property="sysAgendaMainForm.fdConditionFieldFormula" />
	
	<!-- 日程提醒 -->
	<c:forEach items="${sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="sysNotifyRemindMainFormListItem" varStatus="vstatus">
 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdId" value="${sysNotifyRemindMainFormListItem.fdId}" />
 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelId" value="${sysNotifyRemindMainFormListItem.fdModelId}" />
 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdKey" value="${sysNotifyRemindMainFormListItem.fdKey}" />
 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdModelName" value="${mainModelForm.modelClass.name}" />
 	
 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdNotifyType" value="${sysNotifyRemindMainFormListItem.fdNotifyType}" />
 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdBeforeTime" value="${sysNotifyRemindMainFormListItem.fdBeforeTime}" />
 	<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vstatus.index}].fdTimeUnit" value="${sysNotifyRemindMainFormListItem.fdTimeUnit}" />
   </c:forEach> 
</c:if>
