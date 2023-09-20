<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysOrgPerson" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdName" title="${lfn:message('sys-organization:sysOrgPerson.fdName')}" escape="false">
            <c:out value="${sysOrgPerson.fdName}" />
        </list:data-column>
        <list:data-column col="fdNo" title="${lfn:message('sys-organization:sysOrgPerson.fdNo')}" escape="false">
            <c:out value="${sysOrgPerson.fdNo}" />
        </list:data-column>
        <list:data-column col="fdParentOrgName" title="${lfn:message('sys-organization:sysOrgOrg.fdName')}" escape="false">
        	<c:if test="${sysOrgPerson.fdParentOrg!=null}">
        		<c:out value="${sysOrgPerson.fdParentOrg.fdName}" />
        	</c:if>
        </list:data-column>
        <list:data-column col="fdParentName " title="${lfn:message('sys-organization:sysOrgPerson.fdParent')}" escape="false">
        	<c:if test="${sysOrgPerson.fdParent!=null}">
        		<c:out value="${sysOrgPerson.fdParent.fdName}" />
        	</c:if>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
