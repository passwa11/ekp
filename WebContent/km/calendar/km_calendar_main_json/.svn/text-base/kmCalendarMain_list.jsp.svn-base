<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %> 
<json:object>
	<json:property name="errcode" value="0"></json:property>
	<json:property name="errmsg" value="ok"></json:property>
	<json:array name="data" items="${calendars}" var="calendar">
		<json:object>
			<json:property name="fdId" value="${calendar.fdId }"></json:property>
			<json:property name="docSubject" value="${calendar.docSubject }"></json:property>
			<json:property name="fdIsAlldayevent" value="${calendar.fdIsAlldayevent }"></json:property>
			<json:property name="docStartTime" value="${calendar.docStartTime.time }"></json:property>
			<json:property name="docStartTimeStr" value="${calendar.docStartTimeStr }"></json:property>
			<json:property name="docFinishTime" value="${calendar.docFinishTime.time }"></json:property>
			<json:property name="docFinishTimeStr" value="${calendar.docFinishTimeStr }"></json:property>
			<c:if test="${not empty calendar.docLabel }">
				<json:property name="labelId" value="${calendar.docLabel.fdId }"></json:property>
				<json:property name="labelName" value="${calendar.docLabel.fdName }"></json:property>
				<json:property name="labelColor" value="${calendar.docLabel.fdColor }"></json:property>
			</c:if>
			<c:if test="${empty calendar.docLabel }">
				<json:property name="labelName" value="${lfn:message('km-calendar:kmCalendar.nav.title')}"></json:property>
			</c:if>
			<json:property name="docCreatorId" value="${calendar.docCreator.fdLoginName }"></json:property>
			<json:property name="docCreatorName" value="${calendar.docCreator.fdName }"></json:property>
			<json:property name="docOwnerId" value="${calendar.docOwner.fdLoginName }"></json:property>
			<json:property name="docOwnerName" value="${calendar.docOwner.fdName }"></json:property>
			<json:property name="docCreateTime" value="${calendar.docCreateTime.time }"></json:property>
			<json:property name="docCreateTimeStr" value="${calendar.docCreateTimeStr }"></json:property>
			<json:property name="fdLocation" value="${calendar.fdLocation }"></json:property>
			<json:property name="fdRelationUrl" value="${calendar.fdRelationUrl }" escapeXml="false"></json:property>
			<json:property name="viewurl" value="${calendar.viewurl }" escapeXml="false"></json:property>
			<json:property name="recurrenceStr" value="${calendar.fdRecurrenceStr }"></json:property>
			<json:property name="fdAuthorityType" value="${calendar.fdAuthorityType }"></json:property>
			<json:property name="isShared" value="${calendar.isShared }"></json:property>
			<json:property name="createdFrom" value="${calendar.createdFrom }"></json:property>
			<json:array name="reminds" var="remind" items="${calendar.sysNotifyRemindMainContextModel.sysNotifyRemindMainList }">
				<json:object>
					<json:property name="fdNotifyType" value="${remind.fdNotifyType }"></json:property>
					<json:property name="fdTimeUnit" value="${remind.fdTimeUnit }"></json:property>
					<json:property name="fdBeforeTime" value="${remind.fdBeforeTime }"></json:property>
				</json:object>
			</json:array>
		</json:object>
	</json:array>
</json:object>
