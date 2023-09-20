<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysHelpConfig" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column property="fdModuleName" title="${lfn:message('sys-help:sysHelpConfig.fdModuleName')}" />
        <list:data-column property="fdModulePath" title="${lfn:message('sys-help:sysHelpConfig.fdModulePath')}" />
        <list:data-column style="width:50%" property="fdName" title="${lfn:message('sys-help:sysHelpConfig.fdName')}" />
        <list:data-column property="fdUrl" title="${lfn:message('sys-help:sysHelpConfig.fdUrl')}" />
        <list:data-column col="fdStatus.name" title="${lfn:message('sys-help:sysHelpConfig.fdStatus')}">
            <sunbor:enumsShow value="${sysHelpConfig.fdStatus}" enumsType="common_yesno" />
        </list:data-column>
        <%-- 创建者 --%>
		<list:data-column property="docCreator.fdName" title="${ lfn:message('sys-help:sysHelpConfig.docCreator') }">
			<c:out value="${sysHelpConfig.docCreator.fdName}" />
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
