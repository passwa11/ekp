<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="lbpmTemplateChangeLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <%-- 模板名称 --%>
        <list:data-column property="fdName" title="${lfn:message('sys-lbpmservice-changelog:lbpmTemplateChangeLog.fdTemplateName')}">
            <%--<c:out value="${lbpmTemplateChangeLog.fdName}"/>--%>
        </list:data-column>
        <%-- 流程定义版本号 --%>
        <list:data-column col="fdVersion" title="${lfn:message('sys-lbpmservice-changelog:lbpmTemplateChangeLog.fdVersion')}">
            V<c:out value="${lbpmTemplateChangeLog.fdVersion}"/>
            <c:if test="${'true' eq lbpmTemplateChangeLog.isLatestVersion}">
				(当前版本)
			</c:if>
        </list:data-column>
        <list:data-column col="docCreator.name"
                          title="${lfn:message('sys-lbpmservice-changelog:lbpmTemplateChangeLog.docCreator')}" escape="false">
            <c:out value="${lbpmTemplateChangeLog.docCreator.fdName}"/>
        </list:data-column>
        <list:data-column col="docCreateTime"
                          title="${lfn:message('sys-lbpmservice-changelog:lbpmTemplateChangeLog.docCreateTime')}">
            <kmss:showDate value="${lbpmTemplateChangeLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIp" title="${lfn:message('sys-lbpmservice-changelog:lbpmTemplateChangeLog.fdIp')}">
            <c:out value="${lbpmTemplateChangeLog.fdIp}"/>
        </list:data-column>
        <c:if test="${lbpmTemplateChangeLog.authArea.fdName ne null}">
			<list:data-column col="authAreaName" title="${ lfn:message('sys-lbpmservice-support:lbpmPrivilegeLog.authAreaName') }">
				<c:out value="${lbpmTemplateChangeLog.authArea.fdName}" />
			</list:data-column>
		</c:if>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}"/>
</list:data>
