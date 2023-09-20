<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="sysLogExport" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdOperator.name" title="${lfn:message('sys-log:sysLogExport.fdOperator')}" escape="false">
            <c:out value="${sysLogExport.fdOperator.fdName}" />
        </list:data-column>
        <list:data-column col="fdOperator.id" escape="false">
            <c:out value="${sysLogExport.fdOperator.fdId}" />
        </list:data-column>
        <list:data-column col="fdExportDate" title="${lfn:message('sys-log:sysLogExport.fdExportDate')}">
            <kmss:showDate value="${sysLogExport.fdExportDate}" type="datetime"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdLogType.name" title="${lfn:message('sys-log:sysLogExport.fdLogType')}">
            <sunbor:enumsShow value="${sysLogExport.fdLogType}" enumsType="sys_log_export_log_type" />
        </list:data-column>
        <list:data-column col="fdLogType">
            <c:out value="${sysLogExport.fdLogType}" />
        </list:data-column>
        <list:data-column col="fdCount" title="${lfn:message('sys-log:sysLogExport.fdCount')}">
            <c:out value="${sysLogExport.fdCount}" />
        </list:data-column>
        <list:data-column col="fdDownloadExpire" title="${lfn:message('sys-log:sysLogExport.fdDownloadExpire')}">
            <kmss:showDate value="${sysLogExport.fdDownloadExpire}" type="date"></kmss:showDate>
        </list:data-column>
        <list:data-column col="fdState.name" title="${lfn:message('sys-log:sysLogExport.fdState')}">
            <sunbor:enumsShow value="${sysLogExport.fdState}" enumsType="sys_log_export_state" />
        </list:data-column>
        <list:data-column col="fdState">
            <c:out value="${sysLogExport.fdState}" />
        </list:data-column>
        <list:data-column col="operation" title="${lfn:message('list.operation')}" escape="false">
        	<div class="conf_btn_edit">
	            <c:if test="${sysLogExport.fdState == 1}">
	            	<a class="btn_txt" href="javascript:download('${sysLogExport.fdId}');">${lfn:message('button.zip.download')}</a>
	            	<a class="btn_txt" href="javascript:deleteOne('${sysLogExport.fdId}');">${lfn:message('button.delete')}</a>
	            </c:if>
	        </div>
        </list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
