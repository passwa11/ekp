<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysModelingScenes" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <list:data-column col="fdName"  title="${lfn:message('sys-modeling-base:sysModelingScenes.fdName')}">
            <c:out value="${sysModelingScenes.fdName}" />
        </list:data-column>
        <list:data-column col="fdType"  title="${lfn:message('sys-modeling-base:sysModelingScenes.fdType')}">
           <sunbor:enumsShow value="${sysModelingScenes.fdType}" enumsType="sys_modeling_scenes" />
        </list:data-column>
      <list:data-column col="docCreator.name" title="${lfn:message('sys-modeling-base:sysModelingScenes.docCreator')}" escape="false">
            <c:out value="${sysModelingScenes.docCreator.fdName}" />
        </list:data-column>
          <list:data-column col="modelMain.name" title="${lfn:message('sys-modeling-base:sysModelingScenes.modelMain')}" escape="false">
            <c:out value="${sysModelingScenes.modelMain.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${sysModelingScenes.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-modeling-base:sysModelingScenes.docCreateTime')}">
            <kmss:showDate value="${sysModelingScenes.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
