<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="eopBasedataCostCenter" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column property="fdName" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdName')}"  escape="false"/>
        <list:data-column property="fdCode" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdCode')}"  escape="false"/>
         <list:data-column property="fdParent.fdName" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdParent')}"  escape="false"/>
        
        <list:data-column property="fdType.fdName" title="${lfn:message('eop-basedata:eopBasedataCostCenter.fdType')}"  escape="false"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
