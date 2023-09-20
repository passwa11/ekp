<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="hrConfigOvertimeConfig" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdName" title="${lfn:message('hr-config:hrConfigOvertimeConfig.fdName')}" />
        <list:data-column col="fdOrgNames" title="${lfn:message('hr-config:hrConfigOvertimeConfig.fdOrg')}" escape="false">
             <c:forEach var="item" items="${hrConfigOvertimeConfig.fdOrg }" varStatus="vstatus1">
            	${item.fdName};
            </c:forEach>
        </list:data-column>
        <list:data-column col="fdRank" title="${lfn:message('hr-config:hrConfigOvertimeConfig.fdRank')}" style="width:200px" escape="false">
             <c:forEach var="item" items="${hrConfigOvertimeConfig.fdRank }" varStatus="vstatus1">
            	${item.fdName};
            </c:forEach>
        </list:data-column>
     <%--    <list:data-column col="fdOrg.id" escape="false">
            <c:out value="${hrConfigOvertimeConfig.fdOrg.fdId}" />
        </list:data-column> --%>
        <list:data-column col="fdOvertimeType.name" title="${lfn:message('hr-config:hrConfigOvertimeConfig.fdOvertimeType')}">
            <c:set value="${ fn:split(hrConfigOvertimeConfig.fdOvertimeType, ';') }" var="array"></c:set>
        	<c:forEach items="${array}" var="fdOvertimeTypeItem" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		<sunbor:enumsShow value="${fdOvertimeTypeItem}" enumsType="hr_config_overtime_type" />
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdOvertimeType">
            <c:out value="${hrConfigOvertimeConfig.fdOvertimeType}" />
        </list:data-column>
        <list:data-column col="fdOvertimeWelfare.name" title="${lfn:message('hr-config:hrConfigOvertimeConfig.fdOvertimeWelfare')}">
            <c:set value="${ fn:split(hrConfigOvertimeConfig.fdOvertimeWelfare, ';') }" var="array"></c:set>
        	<c:forEach items="${array}" var="fdOvertimeWelfareItem" varStatus="status">
        		<c:if test="${status.index!=0}">;</c:if>
        		<sunbor:enumsShow value="${fdOvertimeWelfareItem}" enumsType="hr_config_overrtime_welfare" />
        	</c:forEach>
        </list:data-column>
        <list:data-column col="fdOvertimeWelfare">
            <c:out value="${hrConfigOvertimeConfig.fdOvertimeWelfare}" />
        </list:data-column>
        <list:data-column property="fdWorkTime" title="${lfn:message('hr-config:hrConfigOvertimeConfig.fdWorkTime')}" />
        <list:data-column col="docCreateTime" title="${lfn:message('hr-config:hrConfigOvertimeConfig.docCreateTime')}">
            <kmss:showDate value="${hrConfigOvertimeConfig.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
