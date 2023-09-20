<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingMain"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingMain" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdName" title="${ lfn:message('km-imeeting:kmImeetingMain.fdName') }" escape="false" style="text-align:left;min-width:150px;">
			<span class="com_subject" ><c:out value="${kmImeetingMain.fdName}"/></span>
		</list:data-column>
		<list:data-column col="beforeChangeContent" title="${ lfn:message('km-imeeting:kmImeetingMain.beforeChangeContent') }" escape="false">
			 <c:choose>
					<c:when test="${fn:length(kmImeetingMain.beforeChangeContent)>50 }"><c:out value="${fn:substring(kmImeetingMain.beforeChangeContent,0,49)}..." /></c:when>
					<c:otherwise><c:out value="${kmImeetingMain.beforeChangeContent}" /></c:otherwise>
			</c:choose> 
		</list:data-column>
		<list:data-column headerClass="width80" col="fdHost" title="${ lfn:message('km-imeeting:kmImeetingMain.fdHost') }" escape="false">
		   <ui:person personId="${kmImeetingMain.fdHost.fdId}" personName="${kmImeetingMain.fdHost.fdName}"></ui:person>
		   <c:out value="${kmImeetingMain.fdOtherHostPerson}"/>
		</list:data-column> 
		<list:data-column headerClass="width160" col="fdPlace" title="${ lfn:message('km-imeeting:kmImeetingMain.fdPlace') }" escape="false">
			<c:if test="${kmImeetingMain.isCloud }">
				<img src="${LUI_ContextPath}/km/imeeting/resource/images/icon_video.png" />		
			</c:if>
		  <c:out value="${kmImeetingMain.fdPlace.fdName}"/> <c:out value="${kmImeetingMain.fdOtherPlace}"/>
		</list:data-column>
		<list:data-column headerClass="width160" col="fdDate" title="${lfn:message('km-imeeting:kmImeetingMain.fdDate') }" escape="false">
			<kmss:showDate value="${kmImeetingMain.fdHoldDate}" type="datetime" /> 
			<br/>
			~ <kmss:showDate value="${kmImeetingMain.fdFinishDate}" type="datetime" /> 
		</list:data-column>
		<list:data-column headerClass="width100" col="fdHoldDate" title="${ lfn:message('km-imeeting:kmImeetingMain.fdHoldDate') }">
		   <kmss:showDate value="${kmImeetingMain.fdHoldDate}" type="datetime" />
		</list:data-column>
		<list:data-column headerClass="width100" col="fdFinishDate" title="${ lfn:message('km-imeeting:kmImeetingMain.fdFinishDate') }">
		   <kmss:showDate value="${kmImeetingMain.fdFinishDate}" type="datetime" /> 
		</list:data-column>
		<list:data-column headerClass="width80" col="docCreator.fdName" title="${ lfn:message('km-imeeting:kmImeetingMain.docCreator') }" escape="false">
			<ui:person personId="${kmImeetingMain.docCreator.fdId}" personName="${kmImeetingMain.docCreator.fdName}"></ui:person>
		</list:data-column>
		<list:data-column headerClass="width100" property="docDept.fdName" title="${ lfn:message('km-imeeting:kmImeetingMain.docDept') }">
		</list:data-column>
		<%--会议状态：发布状态下显示未召开、进行中、已召开....其他情况显示会议状态 #9104--%>
		<list:data-column headerClass="width60" col="docStatus" title="${ lfn:message('km-imeeting:kmImeetingMain.docStatus') }">
			<c:if test="${kmImeetingMain.docStatus=='30' }">
				<%
					if(pageContext.getAttribute("kmImeetingMain")!=null){
						KmImeetingMain kmImeetingMain = (KmImeetingMain)pageContext.getAttribute("kmImeetingMain");
						Date now=new Date(),
						 	holdDate=kmImeetingMain.getFdHoldDate(),
						 	finishDate=kmImeetingMain.getFdFinishDate();
						Boolean isBegin=false,isEnd=false;
						String docStatus=ResourceUtil.getString("kmImeeting.status.publish.unHold", "km-imeeting");
						// 会议已开始
						if (holdDate.getTime() < now.getTime()) {
							isBegin = true;
						}
						// 会议已结束
						if (finishDate.getTime() < now.getTime()) {
							isEnd = true;
						}
						if(isBegin==true && isEnd==false){
							docStatus=ResourceUtil.getString("kmImeeting.status.publish.holding", "km-imeeting");
						}
						if(isEnd==true){
							docStatus=ResourceUtil.getString("kmImeeting.status.publish.hold", "km-imeeting");
						}
						request.setAttribute("docStatus", docStatus);
					}
				%>
				<c:out value="${docStatus}"></c:out>
			</c:if>
			<c:if test="${kmImeetingMain.docStatus!='30' }">
				<sunbor:enumsShow value="${kmImeetingMain.docStatus}" bundle="km-imeeting" enumsType="km_imeeting_main_doc_status" />
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width100" col="docPublishTime" title="${ lfn:message('km-imeeting:kmImeetingMain.docPublishTime') }">
		   <kmss:showDate value="${kmImeetingMain.docPublishTime}" type="datetime" />
		</list:data-column>
		<list:data-column headerClass="width100" col="docCreateTime" title="${ lfn:message('km-imeeting:kmImeetingMain.docCreateTime') }">
		   <kmss:showDate value="${kmImeetingMain.docCreateTime}" type="datetime" />
		</list:data-column>
		<list:data-column headerClass="width50" col="isNotify" title="${ lfn:message('km-imeeting:kmImeetingMain.isNotify')}">
		    <c:if test="${kmImeetingMain.isNotify == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.isNotify == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="isCloud" title="${ lfn:message('km-imeeting:kmImeetingMain.isCloud')}">
		    <c:if test="${kmImeetingMain.isCloud == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.isCloud == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="fdSummaryFlag" title="${ lfn:message('km-imeeting:kmImeetingMain.fdSummaryFlag')}">
		    <c:if test="${kmImeetingMain.fdSummaryFlag == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.fdSummaryFlag == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="fdChangeMeetingFlag" title="${ lfn:message('km-imeeting:kmImeetingMain.fdChangeMeetingFlag')}">
		    <c:if test="${kmImeetingMain.fdChangeMeetingFlag == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.fdChangeMeetingFlag == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="authAttNocopy" title="${ lfn:message('km-imeeting:kmImeetingMain.authAttNocopy')}">
		    <c:if test="${kmImeetingMain.authAttNocopy == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.authAttNocopy == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="authAttNodownload" title="${ lfn:message('km-imeeting:kmImeetingMain.authAttNodownload')}">
		    <c:if test="${kmImeetingMain.authAttNodownload == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.authAttNodownload == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="authAttNoprint" title="${ lfn:message('km-imeeting:kmImeetingMain.authAttNoprint')}">
		    <c:if test="${kmImeetingMain.authAttNoprint == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.authAttNoprint == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="authChangeReaderFlag" title="${ lfn:message('km-imeeting:kmImeetingTemplate.authChangeReaderFlag')}">
		    <c:if test="${kmImeetingMain.authChangeReaderFlag == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.authChangeReaderFlag == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="authChangeAtt" title="${ lfn:message('km-imeeting:kmImeetingTemplate.authChangeAtt')}">
		    <c:if test="${kmImeetingMain.authChangeAtt == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingMain.authChangeAtt == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
				<list:data-column headerClass="width100" col="fdSummaryCompleteTime" title="${ lfn:message('km-imeeting:kmImeetingMain.fdSummaryCompleteTime') }">
			 <kmss:showDate value="${kmImeetingMain.fdSummaryCompleteTime}" type="date" />
		</list:data-column>
		<list:data-column headerClass="width50" col="fdIsHurrySummary" title="${ lfn:message('km-imeeting:kmImeetingMain.fdIsHurrySummary')}">
		   			<c:choose>
						<c:when test="${empty kmImeetingMain.fdIsHurrySummary}">
							<bean:message  bundle="km-imeeting" key="kmMeeting.null"/>
						</c:when>
						<c:otherwise>
							<c:if test="${kmImeetingMain.fdIsHurrySummary == true}">
						    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
							</c:if>
							<c:if test="${kmImeetingMain.fdIsHurrySummary == false}">
								<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
							</c:if>
						</c:otherwise>
					</c:choose> 
		</list:data-column>
		<list:data-column headerClass="width50" col="fdNotifyType" title="${ lfn:message('km-imeeting:kmImeetingMain.fdNotifyType')}">
		    <c:if test="${kmImeetingMain.fdNotifyType == 1}">
		    	<bean:message  bundle="km-imeeting" key="enumeration_km_imeeting_main_fd_notify_type_auto"/>
			</c:if>
			<c:if test="${kmImeetingMain.fdNotifyType == 2}">
				<bean:message  bundle="km-imeeting" key="enumeration_km_imeeting_main_fd_notify_type_self"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="syncDataToCalendarTime" title="${ lfn:message('km-imeeting:kmImeetingMain.syncDataToCalendarTime')}">
		    <c:if test="${kmImeetingMain.syncDataToCalendarTime == 'noSync'}">
		    	<bean:message  bundle="km-imeeting" key="kmImeetingMain.syncDataToCalendarTime.noSync"/>
			</c:if>
			<c:if test="${kmImeetingMain.syncDataToCalendarTime == 'sendNotify'}">
				<bean:message  bundle="km-imeeting" key="kmImeetingMain.syncDataToCalendarTime.flowSubmit"/>
			</c:if>
			<c:if test="${kmImeetingMain.syncDataToCalendarTime == 'personAttend'}">
				<bean:message  bundle="km-imeeting" key="kmImeetingMain.syncDataToCalendarTime.personAttend"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="fdHoldDuration" title="${ lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}" escape="false">
				<%
					if(pageContext.getAttribute("kmImeetingMain")!=null){
						KmImeetingMain kmImeetingMain = (KmImeetingMain)pageContext.getAttribute("kmImeetingMain");
						Double fdHoldDuration = kmImeetingMain.getFdHoldDuration();
						Double hour = 0d;
						if (fdHoldDuration !=null) {
							Double time = new Double(fdHoldDuration);
							Double division = 3600d * 1000d;
							hour = time / division;
						}
						request.setAttribute("hour", hour);
					}
				%>
				<c:set var="tags" value="${fn:split(hour, '.')}"/>
				<c:forEach items="${tags}" var="tag" varStatus="vstatus">
					<c:if test="${tag > 0 && vstatus.count == 1}">
						<c:out value="${tag}小时"></c:out>
					</c:if>
					<c:if test="${tag > 0 && vstatus.count == 2}">
						<c:out value="${tag*6}分钟"></c:out>
					</c:if>
				 </c:forEach> 
		</list:data-column>
		<list:data-action-column name="edit" type="ajax" uri=""/>
		<list:data-action-column name="del" type="ajax" uri=""/>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>