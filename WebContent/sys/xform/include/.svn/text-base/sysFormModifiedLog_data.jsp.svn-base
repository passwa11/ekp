<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysFormModifiedLog" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId"/>
        <list:data-column property="fdModelId"/>
        <list:data-column col="index">
            ${status+1}
        </list:data-column>
        <%-- 模板名称 --%>
        <list:data-column property="fdName" title="${lfn:message('sys-xform-base:sysFormModifiedLog.fdTemplateName')}">
            <%--<c:out value="${sysFormModifiedLog.fdName}"/>--%>
        </list:data-column>
        <%-- 表单版本号 --%>
        <list:data-column col="fdFormVersion" title="${lfn:message('sys-xform-base:sysFormModifiedLog.fdFormVersion')}">
            V<c:out value="${sysFormModifiedLog.fdFormVersion}"/>
            <c:if test="${'true' eq sysFormModifiedLog.isLatestVersion}">
				(当前版本)
			</c:if>
        </list:data-column>
        <list:data-column col="docCreator.name"
                          title="${lfn:message('sys-xform-base:sysFormModifiedLog.docCreator')}" escape="false">
            <c:out value="${sysFormModifiedLog.docCreator.fdName}"/>
        </list:data-column>
        <list:data-column col="docCreateTime"
                          title="${lfn:message('sys-xform-base:sysFormModifiedLog.docCreateTime')}">
            <kmss:showDate value="${sysFormModifiedLog.docCreateTime}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdIp" title="${lfn:message('sys-xform-base:sysFormModifiedLog.fdIp')}">
            <c:out value="${sysFormModifiedLog.fdIp}"/>
        </list:data-column>
         <c:if test="${sysFormModifiedLog.authArea.fdName ne null}">
			<list:data-column col="authAreaName" title="${ lfn:message('sys-xform-base:sysFormModifiedLog.authAreaName') }">
				<c:out value="${sysFormModifiedLog.authArea.fdName}" />
			</list:data-column>
		</c:if>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}"/>
</list:data>
