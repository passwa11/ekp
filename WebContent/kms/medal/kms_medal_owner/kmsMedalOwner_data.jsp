<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.kms.medal.model.KmsMedalOwner"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" 
	prefix="person"%>
<list:data>
	<list:data-columns var="kmsMedalOwner" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column property="docHonoursTime" title="${ lfn:message('kms-medal:kmsMedalOwner.docHonoursTime') }">
		</list:data-column>
		<list:data-column col="docElement.fdId" title="${ lfn:message('kms-medal:kmsMedalOwner.docElement') }">
			<c:out value="${kmsMedalOwner.docElement.fdId}" />
		</list:data-column>
		<list:data-column col="docElement.fdName" title="${ lfn:message('kms-medal:kmsMedalOwner.docElement') }">
			<c:out value="${kmsMedalOwner.docElement.fdName}" />
		</list:data-column>
		<list:data-column col="fdMedal.fdName" title="${ lfn:message('kms-medal:kmsMedalOwner.fdMedal') }">
			<c:out value="${kmsMedalOwner.fdMedal.fdName}" />
		</list:data-column>
		<list:data-column col="imgUrl">
			<%
				Object basedocObj = pageContext.getAttribute("kmsMedalOwner");
				if(basedocObj != null) {
					KmsMedalOwner owner = (KmsMedalOwner)basedocObj;
			%>
			<person:headimageUrl personId="<%=owner.getDocElement().getFdId()%>" size="m"/>
			<%
				}
			%>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>
