<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataMaterial" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdName')}" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdCode')}" />
        <list:data-column col="fdType.name" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdType')}" escape="false">
            <c:out value="${eopBasedataMaterial.fdType.fdName}" />
        </list:data-column>
        <list:data-column col="fdType.id" escape="false">
            <c:out value="${eopBasedataMaterial.fdType.fdId}" />
        </list:data-column>
        <list:data-column property="fdPrice" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdPrice')}" />
        <list:data-column col="fdUnit" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdUnit')}" >
        	<c:if test="${not empty eopBasedataMaterial.fdUnit}">
        		${eopBasedataMaterial.fdUnit.fdName}
        	</c:if>
        </list:data-column>
        <list:data-column col="fdStatus.name" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdStatus')}">
            <sunbor:enumsShow value="${eopBasedataMaterial.fdStatus}" enumsType="eop_basedata_mate_status" />
        </list:data-column>
        <list:data-column col="fdStatus">
            <c:out value="${eopBasedataMaterial.fdStatus}" />
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
