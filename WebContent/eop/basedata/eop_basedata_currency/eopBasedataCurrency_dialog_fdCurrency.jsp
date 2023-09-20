<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCurrency" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCurrency.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCurrency.fdCode')}"  escape="false"/>
        <list:data-column property="fdAbbreviation" title="${lfn:message('eop-basedata:eopBasedataCurrency.fdAbbreviation')}"  escape="false"/>
        <list:data-column property="fdEnglishName" title="${lfn:message('eop-basedata:eopBasedataCurrency.fdEnglishName')}"  escape="false"/>
        <list:data-column property="fdSymbol" title="${lfn:message('eop-basedata:eopBasedataCurrency.fdSymbol')}"  escape="false"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
