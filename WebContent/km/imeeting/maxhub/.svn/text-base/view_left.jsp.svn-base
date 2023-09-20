<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.km.imeeting.forms.KmImeetingMainForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<section class="mhui-template-moduleL" style="position: relative; top: 0.8rem;">

<%
	KmImeetingMainForm form = (KmImeetingMainForm)request.getAttribute("kmImeetingMainForm");

	if(form.getFdAssistPersonNames() != "") {
		String[] assistPersonNames = form.getFdAssistPersonNames().split(";");
		String[] assistPersonIds = form.getFdAssistPersonIds().split(";");

		request.setAttribute("assistPersonNames", assistPersonNames);
		request.setAttribute("assistPersonIds", assistPersonIds);
	}
	
	if(form.getFdParticipantPersonNames() != "") {
		String[] participantPersonNames = form.getFdParticipantPersonNames().split(";");
		String[] participantPersonIds = form.getFdParticipantPersonIds().split(";");

		request.setAttribute("participantPersonNames", participantPersonNames);
		request.setAttribute("participantPersonIds", participantPersonIds);
	}

%>

	<dl class="mhui-information-board" style="overflow: hidden; padding-bottom: 3.6rem;">
	
		<%-- 会议时间 --%>
		<dd style="margin-top: 3.2rem;">
		  <span class="title" style="padding-bottom: 0;"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/></span>
		  <span class="content" style="font-size: 1.625rem;" id="imeetingDuration">-</span>
		</dd>
		
		<%-- 会议地点 --%>
		<dd>
		  <span class="title" style="padding-bottom: 0;"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdPlace"/></span>
		  <span class="content" style="font-size: 1.625rem;">						
		  	<c:out value="${kmImeetingMainForm.fdPlaceName }"></c:out>
			<c:if test="${not empty kmImeetingMainForm.fdOtherPlace }">
			  <c:out value="${kmImeetingMainForm.fdOtherPlace }"></c:out>
			</c:if>
		  </span>
		</dd>
		
		<dd style="flex: 1; overflow: auto;">
			<dl>
				<%-- 会议主持人 --%>
				<dd>
				  <span class="title split"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdHost"/></span>
				  <div class="content">
					<ul class="mhui-avatar-list">
						<li class="mhui-avatar-list-item">
							<span class="mhui-avatar mhui-avatar-circle"><img src="<person:headimageUrl contextPath="true" personId="${kmImeetingMainForm.fdHostId }"/>"></span>
							<span class="txt"><xform:address propertyName="fdHostName" propertyId="fdHostId" orgType="ORG_TYPE_PERSON" style="width:95%;"></xform:address></span>
						</li>
					</ul>
				  </div>
				</dd>
				
				<%--会议纪要人--%>
				<c:if test="${kmImeetingMainForm.fdSummaryInputPersonName != null}">
					<dd>
					  <span class="title split">
					  	<bean:message bundle="km-imeeting" key="kmImeetingMain.fdSummaryInputPerson"/>
					  </span>
					  <div class="content">
	  					<ul class="mhui-avatar-list">
							<li class="mhui-avatar-list-item">
								<span class="mhui-avatar mhui-avatar-circle"><img src="<person:headimageUrl contextPath="true" personId="${kmImeetingMainForm.fdSummaryInputPersonId }"/>"></span>
								<span class="txt">${kmImeetingMainForm.fdSummaryInputPersonName}</span>
							</li>
						</ul>
					  </div>
					</dd>
				</c:if>
				
				<%-- 参会人 --%>
 				<c:if test="${ not empty kmImeetingMainForm.fdAttendPersonNames }">
					<dd>
					  <span class="title split"><bean:message bundle="km-imeeting" key="kmImeetingMain.fdAttendPersons"/></span>
					  <div class="content">
						<ul id="attendPersonList"
							data-dojo-type="mhui/list/ItemListBase"
							data-dojo-mixins="km/imeeting/maxhub/resource/js/list/AttendPersonListMixin"
							data-dojo-props="url:'${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=mhuShowAttendPersons&fdId=${kmImeetingMainForm.fdId}'">
						</ul>
					  </div>
					</dd>
				</c:if>
				
				<c:if test="${kmImeetingMainForm.fdTemplateId != null && kmImeetingMainForm.fdTemplateId != ''}">
					
					<%--会议协助人--%>
		   			<c:if test="${not empty kmImeetingMainForm.fdAssistPersonNames }">
						<dd>
						  <span class="title split">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdAssistPersons"/>
						  </span>
						  <div class="content">
		  					<ul class="mhui-avatar-list">
		  						<c:forEach items="${assistPersonIds}" var="assistPersonId" varStatus="vstatus">
									<li class="mhui-avatar-list-item">
										<span class="mhui-avatar mhui-avatar-circle">
											<img src="<person:headimageUrl contextPath="true" personId="${assistPersonId }"/>">
										</span>
										<span class="txt">${assistPersonNames[vstatus.index]}</span>
									</li>
		  						</c:forEach>
							</ul>
						  </div>
						</dd>
					</c:if>
					
					<%--列席人员--%>
		  			<c:if test="${not empty kmImeetingMainForm.fdParticipantPersonNames }">
						<dd>
						  <span class="title split">
						  	<bean:message bundle="km-imeeting" key="kmImeetingMain.fdParticipantPersons"/>
						  </span>
						  <div class="content">
		  					<ul class="mhui-avatar-list">
		  						<c:forEach items="${participantPersonIds}" var="participantPersonId" varStatus="vstatus">
									<li class="mhui-avatar-list-item">
										<span class="mhui-avatar mhui-avatar-circle">
											<img src="<person:headimageUrl contextPath="true" personId="${participantPersonId }"/>">
										</span>
										<span class="txt">${participantPersonNames[vstatus.index]}</span>
									</li>
		  						</c:forEach>
							</ul>
						  </div>
						</dd>
					</c:if>
					
				</c:if>
				
			</dl>
			
		</dd>
	</dl>

</section>
