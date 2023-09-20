<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataBerth" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataBerth.fdName')}" escape="false" />
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataBerth.fdCode')}" escape="false" />
        <list:data-column col="fdVehicleName" title="${lfn:message('eop-basedata:eopBasedataBerth.fdVehicle')}" escape="false" >
        	${eopBasedataBerth.fdVehicle.fdName}
        </list:data-column>
        <list:data-column col="fdVehicleId" >
        	${eopBasedataBerth.fdVehicle.fdId}
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
