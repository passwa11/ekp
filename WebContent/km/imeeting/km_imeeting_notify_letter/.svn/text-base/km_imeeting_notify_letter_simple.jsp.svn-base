<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="com.landray.kmss.km.imeeting.forms.KmImeetingMainForm"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KKUtil"%>
<%--管理员、会议审批人员看到的会议通知单详情--%>
<div style="float: right;margin:10px;">
	<span>
		<bean:message bundle="km-imeeting" key="kmImeetingMain.docStatus"/>：
		<c:if test="${kmImeetingMainForm.docStatus!='30' && kmImeetingMainForm.docStatus!='41' }">
			<sunbor:enumsShow value="${kmImeetingMainForm.docStatus}" enumsType="common_status" />
		</c:if>
		<%--未召开--%>
		<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==false }">
			<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.unHold"/>
		</c:if>
		<%--正在召开--%>
		<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==true && isEnd==false }">
			<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.holding"/>
		</c:if>
		<%--已召开--%>
		<c:if test="${kmImeetingMainForm.docStatus=='30' && isBegin==true && isEnd==true }">
			<bean:message bundle="km-imeeting" key="kmImeeting.status.publish.hold"/>
		</c:if>
		<%--已取消--%>
		<c:if test="${kmImeetingMainForm.docStatus=='41' }">
			<bean:message bundle="km-imeeting" key="kmImeeting.status.cancel"/>
		</c:if>
	</span>
</div>
<div class="meeting_content_frame">
	<p class="meeting_title"><c:out value="${kmImeetingMainForm.fdName }" /></p>
	<%if(ISysAuthConstant.IS_AREA_ENABLED) { %>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message key="sysAuthArea.authArea" bundle="sys-authorization" />
		</label>
		<div class="meeting_row_content">
			<xform:text style="width:97%" property="authAreaName" showStatus="view" />	
		</div>
	</div>
	<%} %>
	<%-- 会议时间 --%>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
		</label>
		<div class="meeting_row_content">
			<c:out value="${kmImeetingMainForm.fdHoldDate }"></c:out>
			<%-- 会议历时 --%>
			<div class="meeting_duration">
				<label class="meeting_duration_title">
					<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
				</label>
				<div class="meeting_duration_hour">${kmImeetingMainForm.fdHoldDurationHour }小时</div>
			</div>
		</div>
	</div>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedFeedback" />
		</label>
		<div class="meeting_row_content">
			<ui:switch property="fdNeedFeedback" showType="show" checked="${ kmImeetingMainForm.fdNeedFeedback}" />
		</div>
	</div>
	<%
	 	if(KmImeetingConfigUtil.isBoenEnable()|| KKUtil.isKkVideoMeetingEnable()){
	 %>
	  <c:if test="${kmImeetingMainForm.fdNeedFeedback  ne 'false'}">
	 <div class="meeting_row">
	 	<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdFeedBackDeadline"/>
		</label>
		<div class="meeting_row_content">
			<c:out value="${kmImeetingMainForm.fdFeedBackDeadline }"></c:out><font color="red">(请在该截止时间前做回执，否则将无法参会)</font>
		</div>
	</div>	
	</c:if>
	 <%} %>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedPlace" />
		</label>
		<div class="meeting_row_content">
			<ui:switch property="fdNeedPlace" showType="show" checked="${ kmImeetingMainForm.fdNeedPlace}" />
		</div>
	</div>
	<c:if test="${kmImeetingMainForm.fdNeedPlace ne 'false'}">
		<%-- 会议地点 --%>
		<div class="meeting_row">
			<label class="meeting_row_title">
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
			</label>
			<div class="meeting_row_content">
				<c:out value="${kmImeetingMainForm.fdPlaceName }"></c:out>
				<c:if test="${not empty kmImeetingMainForm.fdOtherPlace }">
					<c:out value="${kmImeetingMainForm.fdOtherPlace }"></c:out>
				</c:if>
			</div>
		</div>
	</c:if>
	<%-- 会议主持人 --%>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
		</label>
		<div class="meeting_row_content">
			<c:out value="${kmImeetingMainForm.fdHostName }"></c:out>
		</div>
	</div>
	<%-- 参会人 --%>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendPersons"/>
		</label>
		<div class="meeting_row_content" style="word-break:break-all;">
			<c:out value="${kmImeetingMainForm.fdAttendPersonNames }"></c:out>
		</div>
	</div>
	<%-- 参会内容 --%>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingAim"/>
		</label>
		<div class="meeting_row_content">
			<xform:textarea property="fdMeetingAim"  value="${fn:escapeXml(kmImeetingMainForm.fdMeetingAim)}" showStatus="view" />
		</div>
	</div>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.fdSummaryInputPerson"/>
		</label>
		<div class="meeting_row_content">
			<c:out value="${kmImeetingMainForm.fdSummaryInputPersonName }"></c:out>
		</div>
	</div>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingTemplate.fdEmcee"/>
		</label>
		<div class="meeting_row_content">
			<c:out value="${kmImeetingMainForm.fdEmceeName }"></c:out>
		</div>
	</div>
	<div class="meeting_row">
		<label class="meeting_row_title">
			<bean:message bundle="km-imeeting" key="kmImeetingMain.docDept"/>
		</label>
		<div class="meeting_row_content">
			<c:out value="${kmImeetingMainForm.docDeptName }"></c:out>
		</div>
	</div>
</div>


