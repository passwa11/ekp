<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.km.imeeting.model.KmImeetingSummary"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingSummary" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdName" title="${ lfn:message('km-imeeting:kmImeetingSummary.fdName') }" escape="false" style="text-align:left;min-width:150px;">
			<span class="com_subject" ><c:out value="${kmImeetingSummary.fdName}" /></div>
		</list:data-column>
		<list:data-column headerClass="width80" col="fdHost" title="${ lfn:message('km-imeeting:kmImeetingMain.fdHost') }" escape="false">
		   <ui:person personId="${kmImeetingSummary.fdHost.fdId}" personName="${kmImeetingSummary.fdHost.fdName}"></ui:person>
		   <c:out value="${kmImeetingSummary.fdOtherHostPerson}"/>
		</list:data-column> 
		<list:data-column  headerClass="width140" col="fdPlace" title="${ lfn:message('km-imeeting:kmImeetingSummary.fdPlace') }" escape="false">
		  <c:out value="${kmImeetingSummary.fdPlace.fdName}"/> <c:out value="${kmImeetingSummary.fdOtherPlace}"/>
		</list:data-column>
		<list:data-column headerClass="width140" col="fdDate" title="${lfn:message('km-imeeting:kmImeetingMain.fdDate') }" escape="false">
			<kmss:showDate value="${kmImeetingSummary.fdHoldDate}" type="datetime" /> 
			<br/>
			~ <kmss:showDate value="${kmImeetingSummary.fdFinishDate}" type="datetime" /> 
		</list:data-column>
		<list:data-column headerClass="width140" col="fdHoldDate" title="${ lfn:message('km-imeeting:kmImeetingSummary.fdHoldDate') }">
		   <kmss:showDate value="${kmImeetingSummary.fdHoldDate}" type="datetime" />
		</list:data-column>
		<list:data-column headerClass="width140" col="fdFinishDate" title="${ lfn:message('km-imeeting:kmImeetingSummary.fdFinishDate') }">
		   <kmss:showDate value="${kmImeetingSummary.fdFinishDate}" type="datetime" />
		</list:data-column>
		<list:data-column headerClass="width80" col="docCreator.fdName" title="${ lfn:message('km-imeeting:kmImeetingSummary.docCreator') }" escape="false">
			<ui:person personId="${kmImeetingSummary.docCreator.fdId}" personName="${kmImeetingSummary.docCreator.fdName}"></ui:person>
		</list:data-column>
		<list:data-column headerClass="width140"  col="docCreateTime" title="${ lfn:message('km-imeeting:kmImeetingSummary.docCreateTime') }">
			 <kmss:showDate value="${kmImeetingSummary.docCreateTime}" type="datetime" />
		</list:data-column>
		<list:data-column  headerClass="width140" col="docPublishTime" title="${ lfn:message('km-imeeting:kmImeetingSummary.docPublishTime') }">
			 <kmss:showDate value="${kmImeetingSummary.docPublishTime}" type="datetime" />
		</list:data-column>
		<list:data-column headerClass="width50" col="authAttNocopy" title="${ lfn:message('km-imeeting:kmImeetingSummary.authAttNocopy')}">
		    <c:if test="${kmImeetingSummary.authAttNocopy == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingSummary.authAttNocopy == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="authAttNoprint" title="${ lfn:message('km-imeeting:kmImeetingSummary.authAttNoprint')}">
		    <c:if test="${kmImeetingSummary.authAttNoprint == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingSummary.authAttNoprint == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="authAttNodownload" title="${ lfn:message('km-imeeting:kmImeetingSummary.authAttNodownload')}">
		    <c:if test="${kmImeetingSummary.authAttNodownload == true}">
		    	<bean:message  bundle="km-imeeting" key="kmMeeting.yes"/>
			</c:if>
			<c:if test="${kmImeetingSummary.authAttNodownload == false}">
				<bean:message  bundle="km-imeeting" key="kmMeeting.no"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width50" col="docStatus" title="${ lfn:message('km-imeeting:kmImeetingSummary.docStatus')}">
		    <c:if test="${kmImeetingSummary.docStatus == '00'}">
		    	<bean:message  bundle="km-imeeting" key="kmImeeting.status.abandom"/>
			</c:if>
			<c:if test="${kmImeetingSummary.docStatus == '10'}">
				<bean:message  bundle="km-imeeting" key="kmImeeting.status.draft"/>
			</c:if>
			<c:if test="${kmImeetingSummary.docStatus == '20'}">
		    	<bean:message  bundle="km-imeeting" key="kmImeeting.status.append"/>
			</c:if>
			<c:if test="${kmImeetingSummary.docStatus == '30'}">
		    	<bean:message  bundle="km-imeeting" key="kmImeeting.status.publish"/>
			</c:if>
			<c:if test="${kmImeetingSummary.docStatus == '11'}">
		    	<bean:message  bundle="km-imeeting" key="kmImeeting.status.reject"/>
			</c:if>
		</list:data-column>
		<list:data-column headerClass="width40" col="fdNotifyType" title="${ lfn:message('km-imeeting:kmImeetingSummary.fdNotifyType') }" escape="false">
			        <c:if test="${fn:indexOf(kmImeetingSummary.fdNotifyType,'todo')>-1 }">
							<c:out value="${ lfn:message('km-imeeting:kmImeetingSummary.fdNotifyType.todo') }"/>;
					</c:if>
					<c:if test="${fn:indexOf(kmImeetingSummary.fdNotifyType,'email')>-1 }">
							<c:out value="${ lfn:message('km-imeeting:kmImeetingSummary.fdNotifyType.email') }"/>;
					</c:if>
					<c:if test="${fn:indexOf(kmImeetingSummary.fdNotifyType,'mobile')>-1 }">
							<c:out value="${ lfn:message('km-imeeting:kmImeetingSummary.fdNotifyType.mobile') }"/>;
					</c:if>
			           
			</list:data-column>
		<list:data-column headerClass="width50" col="fdHoldDuration" title="${ lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration')}" escape="false">
				<%
					if(pageContext.getAttribute("kmImeetingSummary")!=null){
						KmImeetingSummary kmImeetingSummary = (KmImeetingSummary)pageContext.getAttribute("kmImeetingSummary");
						Double fdHoldDuration = kmImeetingSummary.getFdHoldDuration();
						Double hour = 0d;
						if (fdHoldDuration !=null) {
							Double time = new Double(fdHoldDuration);
							Double division = 3600d * 1000d;
							hour = time / division;
						}
						int h=(int)hour.doubleValue();
						
						request.setAttribute("hour", h);
						request.setAttribute("minnute", (int)((hour-h)*60+0.5));
					}
				%>
				<c:if test="${hour>0}">
							<c:out value="${hour}小时"></c:out>
				</c:if>
				<c:if test="${minnute>0}">
							<c:out value="${minnute}分钟"></c:out>
				</c:if>
		</list:data-column>	
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>