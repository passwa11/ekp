<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysHelpMain" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column style="width:40%" property="docSubject" title="${lfn:message('sys-help:sysHelpMain.docSubject')}" />
        <list:data-column property="fdModuleName" title="${lfn:message('sys-help:sysHelpMain.fdModuleName')}" />
        <list:data-column col="docCreator.name" title="${lfn:message('sys-help:sysHelpMain.docCreator')}" escape="false">
            <c:out value="${sysHelpMain.docCreator.fdName}" />
        </list:data-column>
        <list:data-column col="docCreator.id" escape="false">
            <c:out value="${sysHelpMain.docCreator.fdId}" />
        </list:data-column>
        <list:data-column col="docCreateTime" title="${lfn:message('sys-help:sysHelpMain.docCreateTime')}">
            <kmss:showDate value="${sysHelpMain.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docPublishTime" title="${lfn:message('sys-help:sysHelpMain.docPublishTime')}">
            <kmss:showDate value="${sysHelpMain.docPublishTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="docStatus" title="${ lfn:message('sys-help:sysHelpMain.docStatus') }">
			<sunbor:enumsShow value="${sysHelpMain.docStatus}" enumsType="common_status" />
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
