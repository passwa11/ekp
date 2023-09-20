<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmImeetingMain" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="docSubject" title="${ lfn:message('km-imeeting:kmImeetingMain.fdName') }" style="text-align:left;min-width:100px" escape="false">
		    <a class="com_subject textEllipsis" title="${kmImeetingMain.fdName}" data-href="${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=${kmImeetingMain.fdId}" target="_blank" onclick="Com_OpenNewWindow(this)">
			  <c:out value="${kmImeetingMain.fdName}"/>
			</a>
		</list:data-column>
		<list:data-column headerClass="width250" styleClass="width250" col="fdHoldDate" title="${ lfn:message('km-imeeting:kmImeetingMain.fdDate') }">
		   <kmss:showDate value="${kmImeetingMain.fdHoldDate}" type="datetime" /> ~
		   <kmss:showDate value="${kmImeetingMain.fdFinishDate}" type="datetime" />
		</list:data-column>
		<list:data-column  col="fdHoldPlace" title="${ lfn:message('km-imeeting:kmImeetingSummary.fdPlace') }" escape="false">
		  <c:out value="${kmImeetingMain.fdPlace.fdName}"/> <c:out value="${kmImeetingMain.fdOtherPlace}"/>
		</list:data-column>
	</list:data-columns>
</list:data>