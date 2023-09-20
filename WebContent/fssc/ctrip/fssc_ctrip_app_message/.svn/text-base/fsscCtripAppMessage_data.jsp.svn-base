<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="fsscCtripAppMessage" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
		<list:data-column property="fdAppKey" title="${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdAppKey')}" />
		<list:data-column property="fdAppSecurity" title="${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdAppSecurity')}" />
		<list:data-column property="fdCorpId" title="${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdCorpId')}" />
		<list:data-column property="fdEmText" title="${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdEmText')}" />
		<list:data-column col="fdSynOrg" title="${lfn:message('fssc-ctrip:fsscCtripAppMessage.fdSynOrg')}">
			<c:forEach var="fdSynOrg" items="${fsscCtripAppMessage.fdSynOrgList }" varStatus="vstatus1">
            	${fdSynOrg.fdName};
            </c:forEach>
		</list:data-column>
		<list:data-column col="docCreatorName" title="${lfn:message('fssc-ctrip:fsscCtripAppMessage.docCreator.fdName')}">
			${fsscCTripAppMessage.docCreator.fdName}
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
