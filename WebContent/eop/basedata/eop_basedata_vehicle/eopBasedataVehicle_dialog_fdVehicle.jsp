<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataVehicle" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataVehicle.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataVehicle.fdCode')}"  escape="false"/>
        <list:data-column col="fdLevel.name" title="${lfn:message('eop-basedata:eopBasedataVehicle.fdLevel')}" escape="false">
            <sunbor:enumsShow value="${eopBasedataVehicle.fdLevel}" enumsType="eop_basedata_berth_level" />
        </list:data-column>
        <list:data-column col="fdLevel">
            <c:out value="${eopBasedataVehicle.fdLevel}" />
        </list:data-column>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
