<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataArea" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdArea" title="${lfn:message('eop-basedata:eopBasedataArea.fdArea')}" />
        <list:data-column col="fdType" title="${lfn:message('eop-basedata:eopBasedataArea.fdType')}" >
        	<sunbor:enumsShow enumsType="eop_basedata_area_type" value="${eopBasedataArea.fdType }"/>
        </list:data-column>
        <list:data-column property="fdCity" title="${lfn:message('eop-basedata:eopBasedataArea.fdCity')}" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
