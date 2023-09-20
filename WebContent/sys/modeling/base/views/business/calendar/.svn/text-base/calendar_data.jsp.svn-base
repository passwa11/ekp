<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysModelingBehavior" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="fdName"  title="${lfn:message('sys-modeling-base:sysModelingBehavior.fdName')}">
            <c:out value="${sysModelingBehavior.fdName}" />
        </list:data-column>
      <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:sysModelingBehavior.docCreator')}" escape="false">
            <c:out value="${sysModelingBehavior.docCreator.fdName}" />
        </list:data-column>
         <list:data-column col="modelMain.name" title="${lfn:message('sys-modeling-base:sysModelingBehavior.modelMain')}" escape="false">
            <c:out value="${sysModelingBehavior.modelMain.fdName}" />
        </list:data-column>
        
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${sysModelingBehavior.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:sysModelingBehavior.docCreateTime')}">
            <kmss:showDate value="${sysModelingBehavior.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
