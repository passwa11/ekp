<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting"%>
<%
	HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
	request.setAttribute("hrToEkpEnable", syncSetting.getHrToEkpEnable());
%>
<list:data>
    <list:data-columns var="hrOrganizationRank" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('hr-organization:hrOrganizationRank.fdName')}" />
        <list:data-column col="fdGrade.name" title="${lfn:message('hr-organization:hrOrganizationRank.fdGrade')}" escape="false">
            <c:out value="${hrOrganizationRank.fdGrade.fdName}" />
        </list:data-column>
        <list:data-column col="fdGrade.id" escape="false">
            <c:out value="${hrOrganizationRank.fdGrade.fdId}" />
        </list:data-column>
        <list:data-column col="docCreator.name" title="${lfn:message('hr-organization:hrOrganizationRank.docCreator')}" escape="false">
            <c:out value="${hrOrganizationRank.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${hrOrganizationRank.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('hr-organization:hrOrganizationRank.docCreateTime')}">
            <kmss:showDate value="${hrOrganizationRank.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdWeight" title="${lfn:message('hr-organization:hrOrganizationGrade.fdWeight')}">
        	<c:out value="${hrOrganizationRank.fdWeight}"/>
        </list:data-column>
        <list:data-column col="fdGradeWeight" title="${lfn:message('hr-organization:hrOrganizationGrade.fdWeight')}">
        	<c:out value="${hrOrganizationRank.fdGrade.fdWeight}"/>
        </list:data-column>                 
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
